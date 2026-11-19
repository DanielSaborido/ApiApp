package com.example.apiapp.ui.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.example.apiapp.network.Genre

@Composable
fun GenresScreen(
    genresUiState: GameViewModel.GenresUiState,
    onGenreClick: (Int, String) -> Unit,
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
fun GenresGridScreen(
    genres: List<Genre>,
    onGenreClick: (Int, String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(150.dp),
        modifier = modifier.padding(horizontal = 4.dp),
        contentPadding = PaddingValues(4.dp)
    ) {
        items(genres, key = { it.id }) { genre ->
            Card(
                onClick = {
                    onGenreClick(genre.id, genre.name)
                },
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = genre.name,
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}