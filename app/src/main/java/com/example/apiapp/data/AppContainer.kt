package com.example.apiapp.data

import com.example.apiapp.network.ApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    var apiAppRepository: ApiAppRepository
}

class DefaultAppContainer() : AppContainer {
    private val BASE_URL = "https://api.rawg.io/api/"
    private val json = Json {
        ignoreUnknownKeys = true
    }

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(
            json.asConverterFactory("application/json".toMediaType())
        )
        .baseUrl(BASE_URL)
        .build()

    private val retrofitService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }

    override var apiAppRepository: ApiAppRepository =
        NetworkApiAppRepository(retrofitService)
}