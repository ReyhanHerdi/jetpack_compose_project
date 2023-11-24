package com.example.movieslist.ui.screen.bookmark

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.movieslist.data.MoviesRespository
import com.example.movieslist.database.FavoriteMovie

class BookmarkViewModel(
    private val repository: MoviesRespository,
) : ViewModel() {


    fun getAllFavoriteMovie(): LiveData<List<FavoriteMovie>> = repository.getAllFavoriteMovie()

    fun deleteFavoriteMovie(favoriteMovie: FavoriteMovie) {
        repository.deleteFavoriteMovie(favoriteMovie)
    }
}