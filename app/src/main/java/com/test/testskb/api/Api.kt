package com.test.testskb.api

import com.test.testskb.model.ContactModel
import io.reactivex.Single
import retrofit2.http.GET

interface Api {

    @GET("generated-01.json")
    fun getList1(): Single<ArrayList<ContactModel>>

    @GET("generated-02.json")
    fun getList2(): Single<ArrayList<ContactModel>>

    @GET("generated-03.json")
    fun getList3(): Single<ArrayList<ContactModel>>
}