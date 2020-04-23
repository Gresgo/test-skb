package com.test.testskb.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.text.SimpleDateFormat
import java.util.*

@Parcelize
data class ContactModel(
    val id: String,
    val name: String,
    val phone: String,
    val height: Float,
    val biography: String,
    val temperament: Temperament,
    val educationPeriod: EducationModel
) : Parcelable {

    fun toStringDate(): String =
        "${formatDate(educationPeriod.start)} - ${formatDate(educationPeriod.end)}"

    fun formatDate(date: Date): String =
        SimpleDateFormat("dd.MM.yyyy", Locale.forLanguageTag("RU")).format(date)

}