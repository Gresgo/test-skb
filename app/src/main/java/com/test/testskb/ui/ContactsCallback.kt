package com.test.testskb.ui

import com.test.testskb.model.ContactModel

interface ContactsCallback {

    fun onContactSelected(contact: ContactModel)

}