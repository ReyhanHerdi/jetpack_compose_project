package com.example.movieslist.data

import com.example.movieslist.model.Movies
import com.example.movieslist.model.MoviesData

class MoviesRespository {
    fun getMovies(): List<Movies> {
        return MoviesData.movies
    }

    fun searchMovies(query: String): List<Movies> {
        return MoviesData.movies.filter {
            it.judul.contains(query, ignoreCase = true)
        }
    }

    companion object {
        @Volatile
        private var instance: MoviesRespository? = null

        fun getInstance(): MoviesRespository =
            instance ?: synchronized(this) {
                MoviesRespository().apply {
                    instance = this
                }
            }
    }
}