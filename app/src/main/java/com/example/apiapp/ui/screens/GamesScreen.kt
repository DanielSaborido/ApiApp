package com.example.apiapp.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
fun GamesGridScreen(
    games: List<Game>,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(150.dp),
        modifier = modifier.padding(horizontal = 4.dp),
        contentPadding = PaddingValues(4.dp)
    ) {
        items(games, key = { it.id }) { game ->
            Card(
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(12.dp)
                ) {
                    Text(
                        text = game.name,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Rating: ${game.rating}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}