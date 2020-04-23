package com.test.testskb.app.dagger

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.test.testskb.api.Api
import com.test.testskb.api.NetworkConnectionInterceptor
import com.test.testskb.util.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.Reusable
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
@Suppress("unused")
object NetworkModule {

    @Provides
    @Reusable
    @JvmStatic
    internal fun provideGson(): Gson =
        GsonBuilder().create()

    @Provides
    @Reusable
    @JvmStatic
    internal fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient().newBuilder()
            .addInterceptor(NetworkConnectionInterceptor())
            .build()

    @Provides
    @Reusable
    @JvmStatic
    internal fun provideRetrofitInterface(gson: Gson, okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()

    @Provides
    @Reusable
    @JvmStatic
    internal fun provideApi(retrofit: Retrofit): Api =
        retrofit.create(Api::class.java)

}