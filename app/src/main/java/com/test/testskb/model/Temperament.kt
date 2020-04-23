package com.test.testskb.model

import com.google.gson.annotations.SerializedName

@Suppress("unused")
enum class Temperament(val type: String) {

    @SerializedName("melancholic")
    MELANCHOLIC("Melancholic"),

    @SerializedName("phlegmatic")
    PHLEGMATIC("Phlegmatic"),

    @SerializedName("sanguine")
    SANGUINE("Sanguine"),

    @SerializedName("choleric")
    CHOLERIC("Choleric")
}