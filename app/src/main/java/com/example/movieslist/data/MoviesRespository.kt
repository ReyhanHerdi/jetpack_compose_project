package com.example.movieslist.data

import android.util.Log
import com.example.movieslist.model.Movies
import com.example.movieslist.model.MoviesData

class MoviesRespository {

    private val movie = mutableListOf<Movies>()
    fun getMovies(): List<Movies> {
        return MoviesData.movies
    }

    fun searchMovies(query: String): List<Movies> {
        return MoviesData.movies.filter {
            it.judul.contains(query, ignoreCase = true)
        }
    }

    fun getMovieById(id: String): Movies {
        Log.d("IDRepo", id)
        return MoviesData.movies.first {
            it.id == id
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