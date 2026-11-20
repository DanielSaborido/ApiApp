package com.example.apiapp.ui

import android.app.Activity
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.apiapp.network.Genre
import com.example.apiapp.ui.screens.GameViewModel
import com.example.apiapp.ui.screens.GenresScreen
import com.example.apiapp.ui.screens.GamesScreen

enum class GameAppScreen(
    val route: String,
    val title: String
) {
    Genres(
        route = "Genres",
        title = "Game Genres"
    ),
    Games(
        route = "Games/{genreId}",
        title = "Games"
    )
}

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun GameApp(
    navController: NavHostController = rememberNavController(),
    gameViewModel: GameViewModel = viewModel(
        factory = GameViewModel.Factory
    )
) {
    val activity = LocalActivity.current as Activity
    val windowSizeClass = calculateWindowSizeClass(activity)

    GameAppContent(
        navController = navController,
        windowSizeClass = windowSizeClass,
        gameViewModel = gameViewModel
    )
}

// Main app content and navigation
@Composable
private fun GameAppContent(
    navController: NavHostController,
    windowSizeClass: WindowSizeClass,
    gameViewModel: GameViewModel
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route
    val selectedGenreId = backStackEntry?.arguments?.getInt("genreId")
    val genres = when (val state = gameViewModel.genresUiState) {
        is GameViewModel.GenresUiState.Success -> state.genres
        else -> emptyList()
    }
    val selectedGenreName = genres.firstOrNull {
        it.id == selectedGenreId
    }?.name ?: "Games"
    val currentTitle = if (currentRoute == GameAppScreen.Games.route) {
        selectedGenreName
    } else {
        GameAppScreen.Genres.title
    }
    val isCompact =
        windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact ||
        windowSizeClass.heightSizeClass == WindowHeightSizeClass.Expanded
    val canNavigateBack = navController.previousBackStackEntry != null
    Scaffold(
        contentWindowInsets = WindowInsets.safeDrawing,
        topBar = {
            GameAppBar(
                title = currentTitle,
                canNavigateBack = canNavigateBack,
                navigateUp = {
                    navController.navigateUp()
                }
            )
        },
        bottomBar = {
            if (isCompact) {
                GameBottomNavigationBar(
                    genres = genres,
                    selectedGenreId = selectedGenreId,
                    onGenreClick = { genreId, genreName ->
                        gameViewModel.getGamesByGenre(genreId)
                        navController.navigate("Games/$genreId") {
                            launchSingleTop = true
                        }
                    }
                )
            }
        }
    ) { innerPadding ->
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            if (!isCompact) {
                GameNavigationRail(
                    genres = genres,
                    selectedGenreId = selectedGenreId,
                    onGenreClick = { genreId, genreName ->
                        gameViewModel.getGamesByGenre(genreId)

                        navController.navigate("Games/$genreId") {
                            popUpTo(GameAppScreen.Genres.route) {
                                inclusive = false
                            }
                            launchSingleTop = true
                        }
                    }
                )
            }
            NavHost(
                navController = navController,
                startDestination = GameAppScreen.Genres.route,
                modifier = Modifier.weight(1f)
            ) {
                composable(GameAppScreen.Genres.route) {
                    GenresScreen(
                        genresUiState = gameViewModel.genresUiState,
                        onGenreClick = { genreId, genreName ->
                            gameViewModel.getGamesByGenre(genreId)
                            navController.navigate("Games/$genreId") {
                                launchSingleTop = true
                            }
                        },
                        modifier = Modifier.fillMaxSize()
                    )
                }
                composable(
                    route = GameAppScreen.Games.route,
                    arguments = listOf(
                        navArgument("genreId") {
                            type = NavType.IntType
                        }
                    )
                ) { entry ->
                    val genreId = entry.arguments?.getInt("genreId") ?: 0
                    if (!isCompact) {
                        Row(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            GamesScreen(
                                gamesUiState = gameViewModel.gamesUiState,
                                modifier = Modifier
                                    .weight(1.4f)
                                    .fillMaxHeight()
                            )
                        }
                    } else {
                        GamesScreen(
                            gamesUiState = gameViewModel.gamesUiState,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
            }
        }
    }
}

// Navigate back to genres
private fun navigateToGenres(
    navController: NavHostController
) {
    if (navController.currentDestination?.route != GameAppScreen.Genres.route) {
        navController.popBackStack(
            GameAppScreen.Genres.route,
            inclusive = false
        )
    }
}

// Top app bar
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun GameAppBar(
    title: String,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit
) {
    TopAppBar(
        title = {
            Text(text = title)
        },
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            }
        }
    )
}

// Mobile bottom navigation

@Composable
private fun GameBottomNavigationBar(
    genres: List<Genre>,
    selectedGenreId: Int?,
    onGenreClick: (Int, String) -> Unit
) {
    val context = LocalContext.current

    NavigationBar {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            verticalAlignment = Alignment.CenterVertically
        ) {
            genres.forEach { genre ->
                val drawableName =
                    "ic_${genre.slug.replace("-", "_")}"
                val drawableId = remember(genre.slug) {
                    context.resources.getIdentifier(
                        drawableName,
                        "drawable",
                        context.packageName
                    )
                }

                Column(
                    modifier = Modifier
                        .width(76.dp)
                        .padding(horizontal = 2.dp, vertical = 4.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(
                            if (selectedGenreId == genre.id)
                                MaterialTheme.colorScheme.secondaryContainer
                            else
                                Color.Transparent
                        )
                        .clickable {
                            onGenreClick(genre.id, genre.name)
                        }
                        .padding(vertical = 6.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (drawableId != 0) {
                        Icon(
                            painter = painterResource(id = drawableId),
                            contentDescription = genre.name,
                            modifier = Modifier.size(24.dp),
                            tint = if (isSystemInDarkTheme()) Color.White else Color.Black
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.Category,
                            contentDescription = genre.name,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = genre.name,
                        style = MaterialTheme.typography.labelSmall,
                        textAlign = TextAlign.Center,
                        maxLines = 1
                    )
                }
            }
        }
    }
}

// Tablet navigation rail
@Composable
private fun GameNavigationRail(
    genres: List<Genre>,
    selectedGenreId: Int?,
    onGenreClick: (Int, String) -> Unit
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .width(100.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        genres.forEach { genre ->
            val drawableName =
                "ic_${genre.slug.replace("-", "_")}"
            val drawableId = remember(genre.slug) {
                context.resources.getIdentifier(
                    drawableName,
                    "drawable",
                    context.packageName
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp, vertical = 4.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(
                        if (selectedGenreId == genre.id)
                            MaterialTheme.colorScheme.secondaryContainer
                        else
                            Color.Transparent
                    )
                    .clickable {
                        onGenreClick(genre.id, genre.name)
                    }
                    .padding(vertical = 10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (drawableId != 0) {
                    Icon(
                        painter = painterResource(id = drawableId),
                        contentDescription = genre.name,
                        modifier = Modifier.size(28.dp),
                        tint = if (isSystemInDarkTheme()) Color.White else Color.Black
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.Category,
                        contentDescription = genre.name,
                        modifier = Modifier.size(28.dp)
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = genre.name,
                    style = MaterialTheme.typography.labelSmall,
                    textAlign = TextAlign.Center,
                    maxLines = 2
                )
            }
        }
    }
}