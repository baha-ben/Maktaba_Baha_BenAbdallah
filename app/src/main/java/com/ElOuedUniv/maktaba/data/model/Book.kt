package com.ElOuedUniv.maktaba.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Book(
    val isbn: String,
    val title: String,
    val nbPages: Int,
    val author: String,
    val imageUrl: String? = null
)
