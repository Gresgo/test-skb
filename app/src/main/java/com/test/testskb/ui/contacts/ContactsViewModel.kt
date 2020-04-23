package com.test.testskb.ui.contacts

import android.annotation.SuppressLint
import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.test.testskb.api.Api
import com.test.testskb.api.NetworkConnectionInterceptor
import com.test.testskb.api.NoConnectionException
import com.test.testskb.base.BaseViewModel
import com.test.testskb.model.ContactModel
import com.test.testskb.storage.StorageManager
import com.test.testskb.util.plusAssign
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ContactsViewModel : BaseViewModel() {

    companion object {
        private const val TAG = "contacts vm"
        private const val fileName = "contacts"
    }

    val isLoading = ObservableField<Boolean>()
    val contactsList = MutableLiveData<ArrayList<ContactModel>>()
    val snackbar = MutableLiveData<String>()
    @Inject lateinit var api: Api
    @Inject lateinit var storageManager: StorageManager
    @Inject lateinit var gson: Gson

    init {
        isLoading.set(true)
        if (loadedRecently()) {
            Log.i(TAG, "Load from file")
            readFile()
        } else {
            Log.i(TAG, "load from net")
            loadData()
        }
    }

    @SuppressLint("CheckResult")
    fun loadData() {
        isLoading.set(true)

        /**
         * maybe its better like this, then we don't clear list if no connection
         * but i like okhttp way more even if list cleared
         */
//        if (!NetworkConnectionInterceptor().connection()) {
//            onError(NoConnectionException())
//            return
//        }
        contactsList.value = arrayListOf()

        val list1 = api.getList1()
        val list2 = api.getList2()
        val list3 = api.getList3()

        Single.merge(list1, list2, list3)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({result ->
                onComplete(result)
            }, {err ->
                onError(err)
            },{
                writeToFile()
            })

    }

    private fun loadedRecently(): Boolean =
        storageManager.checkLatestFile("contacts")

    @SuppressLint("CheckResult")
    private fun writeToFile() {

        Single.fromCallable {
            val list = gson.toJson(contactsList.value)
            storageManager.write(fileName, list)
            true
        }.subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.i(TAG, "loaded to file")
            }, {err ->
                onError(err)
            })

    }

    @SuppressLint("CheckResult")
    private fun readFile() {

        Single.fromCallable {
            val result = storageManager.read(fileName)!!
            gson.fromJson<ArrayList<ContactModel>>(result, object : TypeToken<ArrayList<ContactModel>>(){}.type)
        }.subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({result ->
                contactsList.value = arrayListOf()
                onComplete(result)
                Log.i(TAG, "loaded from file")
            }, {err ->
                onError(err)
            })

    }

    private fun onComplete(result: ArrayList<ContactModel>) {
        contactsList += result
        isLoading.set(false)
    }

    private fun onError(err: Throwable) {
        Log.e(TAG, err.localizedMessage)
        snackbar.value = err.localizedMessage
        snackbar.value = null
        isLoading.set(false)
    }
}