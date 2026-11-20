package com.example.apiapp.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Genre(
    val id: Int,
    val name: String,
    val slug: String? = null,

    @SerialName("image_background")
    val imageBackground: String? = null
)

@Serializable
data class GenresResponse(
    val results: List<Genre>
)