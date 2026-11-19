package com.example.apiapp.network

import retrofit2.http.GET
import retrofit2.http.Query

private const val API_KEY = "6cc6a4e1e5ab407b9bace9a45355c34d"

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