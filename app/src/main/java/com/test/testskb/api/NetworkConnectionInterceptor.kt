package com.test.testskb.api

import android.content.Context
import android.net.ConnectivityManager
import com.test.testskb.app.App
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class NetworkConnectionInterceptor: Interceptor {

    @Inject lateinit var context: Context

    override fun intercept(chain: Interceptor.Chain): Response {
        App.appComponent.inject(this)
        if (!connection()) {
            throw NoConnectionException()
        }

        val builder = chain.request().newBuilder()
        return chain.proceed(builder.build())
    }

    fun connection(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val info = connectivityManager.activeNetworkInfo
        return (info != null && info.isConnected)
    }
}