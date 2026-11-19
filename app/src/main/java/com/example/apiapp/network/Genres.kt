package com.example.apiapp.network

import kotlinx.serialization.Serializable

@Serializable
data class Genre(
    val id: Int,
    val name: String,
    val image_background: String
)

@Serializable
data class GenresResponse(
    val results: List<Genre>
)