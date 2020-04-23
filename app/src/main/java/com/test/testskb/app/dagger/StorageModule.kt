package com.test.testskb.app.dagger

import com.test.testskb.storage.StorageManager
import dagger.Module
import dagger.Provides

@Module
@Suppress("unused")
object StorageModule {

    @Provides
    fun provideStorageManager(): StorageManager = StorageManager()

}