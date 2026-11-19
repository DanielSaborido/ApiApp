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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.apiapp.R
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
                    AsyncImage(
                        model = ImageRequest.Builder( context = LocalContext.current)
                            .data(game.background_image)
                            .crossfade(true)
                            .build(),
                        contentScale = ContentScale.Crop,
                        placeholder = painterResource(R.drawable.loading_img),
                        error = painterResource(R.drawable.ic_broken_image),
                        contentDescription = game.name,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(4.dp))
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