package com.example.apiapp.ui.screens

import android.text.Html
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.apiapp.R
import com.example.apiapp.network.Game

@Composable
fun GameScreen(
    gameDetailsUiState: GameViewModel.GameDetailsUiState,
    modifier: Modifier = Modifier
) {
    when (gameDetailsUiState) {
        is GameViewModel.GameDetailsUiState.Loading ->
            LoadingScreen(modifier)
        is GameViewModel.GameDetailsUiState.Error ->
            ErrorScreen(modifier)
        is GameViewModel.GameDetailsUiState.Success -> {
            GameDetailsContent(
                game = gameDetailsUiState.game,
                modifier = modifier
            )
        }
    }
}

@Composable
private fun GameDetailsContent(
    game: Game,
    modifier: Modifier = Modifier
) {
    val uriHandler = LocalUriHandler.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(360.dp)
        ) {
            AsyncImage(
                model = ImageRequest.Builder( context = LocalContext.current)
                    .data(game.backgroundImage)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(R.drawable.loading_img),
                error = painterResource(R.drawable.ic_broken_image),
                contentDescription = game.name,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black.copy(alpha = 0.45f),
                                Color.Black.copy(alpha = 0.95f)
                            )
                        )
                    )
            )
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(20.dp)
            ) {
                Text(
                    text = game.name,
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "★ ${game.rating}/5",
                        color = Color.White,
                        style = MaterialTheme.typography.titleMedium
                    )
                    game.metacritic?.let {
                        Text(
                            text = "Metacritic: $it",
                            color = Color.White,
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
                game.released?.let {
                    Text(
                        text = "Released: $it",
                        color = Color.White.copy(alpha = 0.85f)
                    )
                }
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "About this game",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = Html.fromHtml(
                    game.description?: "No description available.",
                    Html.FROM_HTML_MODE_COMPACT
                ).toString().ifBlank { "No description available." },
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
            HorizontalDivider()
            Text(
                text = "Game information",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    DetailRow(
                        title = "Developers",
                        value = game.developers.joinToString { it.name }
                    )
                    DetailRow(
                        title = "Publishers",
                        value = game.publishers.joinToString { it.name }
                    )
                }
                VerticalDivider(
                    modifier = Modifier
                        .height(80.dp)
                        .padding(horizontal = 8.dp)
                )
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    DetailRow(
                        title = "Platforms",
                        value = game.platforms.joinToString { it.platform.name }
                    )
                    DetailRow(
                        title = "Genres",
                        value = game.genres.joinToString { it.name }
                    )
                }
            }
            if (!game.website.isNullOrBlank()) {
                Button(
                    onClick = {
                        uriHandler.openUri(game.website)
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Visit official website")
                }
            }
        }
    }
}

@Composable
private fun DetailRow(
    title: String,
    value: String
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}
