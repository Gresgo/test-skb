package com.test.testskb.ui.details

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.test.testskb.model.ContactModel

class DetailsViewModel : ViewModel() {

    val contact = ObservableField<ContactModel>()

    fun setContact(user: ContactModel) {
        contact.set(user)
    }

}