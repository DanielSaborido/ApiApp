package com.example.apiapp.network

import retrofit2.http.GET
import retrofit2.http.Query

private const val API_KEY = "37dea5560e494058945502465024de6a"

interface ApiService {
    @GET("genres")
    suspend fun getGenres(
        @Query("key") key: String = API_KEY
    ): GenresResponse

    @GET("games")
    suspend fun getGamesByGenre(
        @Query("genres") genreId: Int,
        @Query("key") key: String = API_KEY,
        @Query("page_size") pageSize: Int = 20
    ): GamesResponse
}