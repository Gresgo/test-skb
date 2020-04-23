package com.test.testskb.app.dagger

import com.test.testskb.ui.contacts.ContactsViewModel
import dagger.Component

@Component(modules = [NetworkModule::class, StorageModule::class])
interface ViewModelComponent {

    fun inject(contactsViewModel: ContactsViewModel)

    @Component.Builder
    interface Builder {

        fun build(): ViewModelComponent

        fun networkModule(networkModule: NetworkModule): Builder

        fun storageModule(storageModule: StorageModule): Builder

    }

}