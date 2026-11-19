package com.example.apiapp.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Game(
    val id: Int,
    val name: String,
    @SerialName(value = "background_image")
    val background_image: String?,
    val rating: Double
)

@Serializable
data class GamesResponse(
    val results: List<Game>
)