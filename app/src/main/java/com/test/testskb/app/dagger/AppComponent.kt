package com.test.testskb.app.dagger

import com.test.testskb.api.NetworkConnectionInterceptor
import com.test.testskb.app.App
import com.test.testskb.storage.StorageManager
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    fun inject(app: App)

    fun inject(networkConnectionInterceptor: NetworkConnectionInterceptor)
    fun inject(storageManager: StorageManager)

    @Component.Builder
    interface Builder {

        fun build(): AppComponent

        @BindsInstance
        fun appBind(app: App): Builder

        fun appModule(appModule: AppModule): Builder

    }

}