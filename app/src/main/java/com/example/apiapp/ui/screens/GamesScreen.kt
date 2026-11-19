package com.example.apiapp.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.apiapp.network.Game

@Composable
fun GamesScreen(
    gamesUiState: GameViewModel.GamesUiState,
    modifier: Modifier = Modifier
) {
    when (gamesUiState) {
        is GameViewModel.GamesUiState.Loading ->
            LoadingScreen(modifier)

        is GameViewModel.GamesUiState.Error ->
            ErrorScreen(modifier)

        is GameViewModel.GamesUiState.Success ->
            GamesGridScreen(
                games = gamesUiState.games,
                modifier = modifier
            )
    }
}

@Composable
fun GamesGridScreen(games: List<Game>, modifier: Modifier) {
    TODO("Not yet implemented")
}