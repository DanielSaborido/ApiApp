package com.example.apiapp

import android.app.Application
import com.example.apiapp.data.AppContainer
import com.example.apiapp.data.DefaultAppContainer

class GameApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}
