package com.example.projectx.data

import android.media.Rating
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product (
    val id: String,
    val title: String?= null,
    val price: Double?= null,
    val description: String?= null,
    val category: String?= null,
    val image: String?= null,
    val rating: Rating?= null
):Parcelable{
    constructor():this("0")
}

