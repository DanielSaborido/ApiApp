package com.example.apiapp.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Game(
    val id: Int,
    val name: String,
    val slug: String? = null,

    @SerialName("description_raw")
    val description: String? = null,

    @SerialName("background_image")
    val backgroundImage: String? = null,

    val rating: Double = 0.0,
    val released: String? = null,
    val metacritic: Int? = null,
    val website: String? = null,

    val developers: List<Developer> = emptyList(),
    val publishers: List<Publisher> = emptyList(),
    val platforms: List<GamePlatform> = emptyList(),
    val genres: List<Genre> = emptyList()
)

@Serializable
data class GamesResponse(
    val results: List<Game>
)

@Serializable
data class Developer(
    val id: Int? = null,
    val name: String
)

@Serializable
data class Publisher(
    val id: Int? = null,
    val name: String
)

@Serializable
data class GamePlatform(
    val platform: PlatformInfo
)

@Serializable
data class PlatformInfo(
    val id: Int? = null,
    val name: String,
    val slug: String? = null
)