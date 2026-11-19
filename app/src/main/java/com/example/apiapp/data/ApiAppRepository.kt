package com.example.apiapp.data

import com.example.apiapp.network.ApiService
import com.example.apiapp.network.Game
import com.example.apiapp.network.Genre

interface ApiAppRepository {
    suspend fun getGenres(): List<Genre>
    suspend fun getGamesByGenre(genreId: Int): List<Game>
}

class NetworkApiAppRepository(
    private val apiService: ApiService
): ApiAppRepository {
    override suspend fun getGenres(): List<Genre> {
        return apiService.getGenres().results
    }

    override suspend fun getGamesByGenre(
        genreId: Int
    ): List<Game> {
        return apiService.getGamesByGenre(genreId).results
    }
}
