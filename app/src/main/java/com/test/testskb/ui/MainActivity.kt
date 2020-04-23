package com.test.testskb.ui

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.test.testskb.R
import com.test.testskb.model.ContactModel
import com.test.testskb.ui.contacts.ContactsFragment
import com.test.testskb.ui.details.DetailsFragment

class MainActivity : AppCompatActivity(), ContactsCallback {

    companion object {
        private const val TAG_LIST = "list"
        private const val TAG_DETAILS = "details"
        private const val host = R.id.host
        private const val REQUEST_PERMISSION_CODE = 571
    }
    private lateinit var contactsFragment: Fragment
    private lateinit var detailsFragment: Fragment
    private val fm = supportFragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.title = ""

        if ((ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            && (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {

            ActivityCompat.requestPermissions(this, arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ), REQUEST_PERMISSION_CODE)

        } else {
            if (savedInstanceState == null) {
                contactsFragment = ContactsFragment()
                toList()
            } else {
                contactsFragment = fm.findFragmentByTag("tag")!!
            }
        }

    }

    override fun onContactSelected(contact: ContactModel) {
        toDetails(contact)
    }

    private fun toList() {
        supportActionBar?.hide()
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        fm.beginTransaction().replace(
            host, contactsFragment,
            TAG_LIST
        ).commitNow()
    }

    private fun toDetails(contact: ContactModel) {
        supportActionBar?.show()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        detailsFragment = DetailsFragment.newInstance(contact)
        fm.beginTransaction().replace(
            host, detailsFragment,
            TAG_DETAILS
        ).commitNow()
    }

    override fun onBackPressed() {
        val mainFragment = fm.findFragmentByTag(TAG_LIST)
        if (mainFragment != null && mainFragment.isVisible) {
            super.onBackPressed()
        } else {
            toList()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return true
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            REQUEST_PERMISSION_CODE ->
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    contactsFragment = ContactsFragment()
                    toList()
                }
        }

    }

}
