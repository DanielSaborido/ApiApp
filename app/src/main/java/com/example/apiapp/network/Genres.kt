package com.example.apiapp.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Genre(
    val id: Int,
    val name: String,
    val slug: String,
    @SerialName(value = "image_background")
    val image_background: String
)

@Serializable
data class GenresResponse(
    val results: List<Genre>
)