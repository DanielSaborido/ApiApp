package com.example.apiapp.ui.screens

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.apiapp.R
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
                AsyncImage(
                    model = ImageRequest.Builder( context = LocalContext.current)
                        .data(genre.imageBackground)
                        .crossfade(true)
                        .build(),
                    contentScale = ContentScale.Crop,
                    placeholder = painterResource(R.drawable.loading_img),
                    error = painterResource(R.drawable.ic_broken_image),
                    contentDescription = genre.name,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = genre.name,
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}