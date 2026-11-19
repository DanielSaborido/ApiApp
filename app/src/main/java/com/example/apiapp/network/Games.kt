package com.example.apiapp.network

import kotlinx.serialization.Serializable

@Serializable
data class Game(
    val id: Int,
    val name: String,
    val background_image: String?,
    val rating: Double
)

@Serializable
data class GamesResponse(
    val results: List<Game>
)