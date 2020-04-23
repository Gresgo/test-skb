package com.test.testskb.base

import androidx.lifecycle.ViewModel
import com.test.testskb.app.dagger.DaggerViewModelComponent
import com.test.testskb.app.dagger.NetworkModule
import com.test.testskb.app.dagger.StorageModule
import com.test.testskb.app.dagger.ViewModelComponent
import com.test.testskb.ui.contacts.ContactsViewModel

abstract class BaseViewModel : ViewModel() {

    private val vmComponent: ViewModelComponent.Builder = DaggerViewModelComponent
        .builder()
        .networkModule(NetworkModule)

    init {
        inject()
    }

    private fun inject() {
        when (this) {
            is ContactsViewModel -> vmComponent
                .storageModule(StorageModule)
                .build()
                .inject(this)
        }
    }

}