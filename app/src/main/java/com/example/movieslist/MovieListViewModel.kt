package com.example.movieslist

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.movieslist.data.MoviesRespository
import com.example.movieslist.model.Movies
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MovieListViewModel(private val repository: MoviesRespository) : ViewModel() {
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

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(private val repository: MoviesRespository) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MovieListViewModel::class.java)) {
            return MovieListViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

}