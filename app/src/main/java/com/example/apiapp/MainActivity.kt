package com.example.apiapp

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import android.os.Bundle
import com.example.apiapp.ui.GameApp
import com.example.apiapp.ui.screens.GameViewModel

class MainActivity : ComponentActivity() {
    private val viewModel: GameViewModel by viewModels {
        GameViewModel.Factory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GameApp(
                gameViewModel = viewModel
            )
        }
    }
}
