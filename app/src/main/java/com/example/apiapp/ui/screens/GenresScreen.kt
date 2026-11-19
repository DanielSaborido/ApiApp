package com.example.apiapp.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.apiapp.network.Genre

@Composable
fun GenresScreen(
    genresUiState: GameViewModel.GenresUiState,
    onGenreClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    when (genresUiState) {
        is GameViewModel.GenresUiState.Loading ->
            LoadingScreen(modifier)

        is GameViewModel.GenresUiState.Error ->
            ErrorScreen(modifier)

        is GameViewModel.GenresUiState.Success ->
            GenresGridScreen(
                genres = genresUiState.genres,
                onGenreClick = onGenreClick,
                modifier = modifier
            )
    }
}

@Composable
fun GenresGridScreen(genres: List<Genre>, onGenreClick: (Int) -> Unit, modifier: Modifier) {
    TODO("Not yet implemented")
}
