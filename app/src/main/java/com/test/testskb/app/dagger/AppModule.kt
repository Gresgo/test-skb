package com.test.testskb.app.dagger

import android.content.Context
import com.test.testskb.app.App
import dagger.Module
import dagger.Provides
import java.io.File
import javax.inject.Singleton

@Module
@Suppress("unused")
object AppModule {

    @Provides
    @Singleton
    fun provideContext(app: App): Context = app.applicationContext

    @Provides
    @Singleton
    fun provideCache(app: App): File = app.cacheDir

}