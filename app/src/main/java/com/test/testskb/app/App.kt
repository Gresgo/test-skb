package com.test.testskb.app

import android.app.Application
import com.test.testskb.app.dagger.AppComponent
import com.test.testskb.app.dagger.AppModule
import com.test.testskb.app.dagger.DaggerAppComponent

class App : Application() {

    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()

        initDagger()
    }

    private fun initDagger() {
        appComponent = DaggerAppComponent.builder()
            .appBind(this)
            .appModule(AppModule)
            .build()

        appComponent.inject(this)
    }

}