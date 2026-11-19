package com.example.apiapp.ui

import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.apiapp.ui.screens.GameViewModel
import com.example.apiapp.ui.screens.GenresScreen
import com.example.apiapp.ui.screens.GamesScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameApp(
    gameViewModel: GameViewModel = viewModel(
        factory = GameViewModel.Factory
    )
) {
    var selectedGenreId by remember {
        mutableStateOf<Int?>(null)
    }
    var selectedGenreName by remember {
        mutableStateOf("")
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = selectedGenreName.ifEmpty {
                            "Game Genres"
                        }
                    )
                },
                navigationIcon = {
                    if (selectedGenreId != null) {
                        IconButton(
                            onClick = {
                                selectedGenreId = null
                                selectedGenreName = ""
                            }
                        ) {
                            Icon(
                                imageVector = androidx.compose.material.icons.Icons.Default.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        if (selectedGenreId == null) {
            GenresScreen(
                genresUiState = gameViewModel.genresUiState,
                onGenreClick = { genreId, genreName ->
                    selectedGenreId = genreId
                    selectedGenreName = genreName
                    gameViewModel.getGamesByGenre(genreId)
                },
                modifier = Modifier.padding(innerPadding)
            )
        } else {
            GamesScreen(
                gamesUiState = gameViewModel.gamesUiState,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}