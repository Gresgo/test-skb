package com.test.testskb.api

import java.io.IOException

class NoConnectionException: IOException() {

    override fun getLocalizedMessage(): String =
        "Нет подключения к сети"

}