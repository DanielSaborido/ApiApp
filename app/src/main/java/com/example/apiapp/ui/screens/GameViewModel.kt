
package com.example.apiapp.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.apiapp.GameApplication
import com.example.apiapp.data.ApiAppRepository
import com.example.apiapp.network.Genre
import com.example.apiapp.network.Game
import kotlinx.coroutines.launch
import java.io.IOException

class GameViewModel(
    private val apiAppRepository: ApiAppRepository
) : ViewModel() {
    sealed interface GenresUiState {
        data class Success(val genres: List<Genre>) : GenresUiState
        object Error : GenresUiState
        object Loading : GenresUiState
    }
    sealed interface GamesUiState {
        data class Success(val games: List<Game>) : GamesUiState
        object Error : GamesUiState
        object Loading : GamesUiState
    }

    var genresUiState: GenresUiState by mutableStateOf(GenresUiState.Loading)
        private set
    var gamesUiState: GamesUiState by mutableStateOf(GamesUiState.Loading)
        private set

    /**
     * Load genres when the ViewModel is created.
     */
    init {
        getGenres()
    }

    /**
     * Retrieves all genres from the RAWG API.
     */
    fun getGenres() {
        viewModelScope.launch {
            genresUiState = GenresUiState.Loading

            genresUiState = try {
                val genres = apiAppRepository.getGenres()
                GenresUiState.Success(genres)
            } catch (e: IOException) {
                GenresUiState.Error
            }
        }
    }

    /**
     * Retrieves games belonging to the selected genre.
     */
    fun getGamesByGenre(genreId: Int) {
        viewModelScope.launch {
            gamesUiState = GamesUiState.Loading

            gamesUiState = try {
                val games = apiAppRepository.getGamesByGenre(genreId)
                GamesUiState.Success(games)
            } catch (e: IOException) {
                GamesUiState.Error
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    (this[APPLICATION_KEY] as GameApplication)
                val apiAppRepository =
                    application.container.apiAppRepository

                GameViewModel(
                    apiAppRepository = apiAppRepository
                )
            }
        }
    }
}
