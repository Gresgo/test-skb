package com.test.testskb.util

import androidx.lifecycle.MutableLiveData

const val BASE_URL = "https://raw.githubusercontent.com/SkbkonturMobile/mobile-test-droid/master/json/"

operator fun <T> MutableLiveData<ArrayList<T>>.plusAssign(values: List<T>) {
    val value = this.value ?: arrayListOf()
    value.addAll(values)
    this.value = value
}