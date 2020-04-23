package com.test.testskb.storage

import com.test.testskb.app.App
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class StorageManager {

    @Inject lateinit var cache: File

    init {
        App.appComponent.inject(this)
    }

    fun write(fileName: String, data: String) {
        val file = File(cache, fileName)
        val fos = FileOutputStream(file)
        try {
            fos.write(data.toByteArray())
        } finally {
            fos.close()
        }
    }

    fun checkLatestFile(fileName: String): Boolean {
        val file = File(cache, fileName)
        if (!file.exists()) return false
        val difference = Date().time - file.lastModified()
        return (TimeUnit.SECONDS.convert(difference, TimeUnit.MILLISECONDS) < 61)
    }

    fun read(fileName: String): String? {
        val list = File(cache, fileName)
        val result: ByteArray
        if (!list.exists()) return null
        val fis = FileInputStream(list)
        result = ByteArray(list.length().toInt())
        try {
            fis.read(result)
        } finally {
            fis.close()
        }

        return String(result)
    }

}