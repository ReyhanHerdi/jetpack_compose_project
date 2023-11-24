package com.example.movieslist.ui.screen.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieslist.common.UiState
import com.example.movieslist.data.MoviesRespository
import com.example.movieslist.database.FavoriteMovie
import com.example.movieslist.model.Movies
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailViewModel(
    private val repository: MoviesRespository
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<Movies>> = MutableStateFlow(UiState.Loading)

    val uiState: StateFlow<UiState<Movies>> get() = _uiState

    fun getMovieById(id: String) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            _uiState.value = UiState.Success(repository.getMovieById(id))
            Log.d("Check", repository.getMovieById(id).judul)
        }
    }

    fun insertFavoriteMovie(favoriteMovie: FavoriteMovie) {
        repository.insertFavoriteMovie(favoriteMovie)
    }

    fun deleteFavoriteMovie(favoriteMovie: FavoriteMovie) {
        repository.deleteFavoriteMovie(favoriteMovie)
    }

    fun getFavMovieById(id: String): LiveData<List<FavoriteMovie>> = repository.getFavMovieById(id)
}
