package com.example.movieslist.ui.screen.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.movieslist.data.MoviesRespository
import com.example.movieslist.model.Movies
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HomeViewModel(private val repository: MoviesRespository) : ViewModel() {
    private val _groupedMovies = MutableStateFlow(
        repository.getMovies()
            .sortedBy { it.judul }
            .groupBy { it.judul[0] }
    )
    val sortedMovies: StateFlow<Map<Char, List<Movies>>> get() = _groupedMovies

    private val _query = mutableStateOf("")
    val query: State<String> get() = _query

    fun search(newQuery: String) {
        _query.value = newQuery
        _groupedMovies.value = repository.searchMovies(_query.value)
            .sortedBy { it.judul }
            .groupBy { it.judul[0] }
    }
}
