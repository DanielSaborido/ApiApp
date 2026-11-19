package com.example.apiapp

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import android.os.Bundle
import com.example.apiapp.ui.GameApp
import com.example.apiapp.ui.theme.ApiAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ApiAppTheme {
                GameApp()
            }
        }
    }
}
