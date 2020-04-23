package com.test.testskb.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class EducationModel(
    val start: Date,
    val end: Date
) : Parcelable