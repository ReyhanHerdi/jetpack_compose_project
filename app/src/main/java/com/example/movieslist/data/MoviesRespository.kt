package com.example.movieslist.data

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import com.example.movieslist.database.FavoriteMovie
import com.example.movieslist.database.FavoriteMovieDao
import com.example.movieslist.database.FavoriteMovieDatabase
import com.example.movieslist.model.Movies
import com.example.movieslist.model.MoviesData
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class MoviesRespository(application: Application) {
    private val mFavoriteMovieDao: FavoriteMovieDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavoriteMovieDatabase.getDatabase(application)
        mFavoriteMovieDao = db.favoriteMovie()
    }

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

    fun getAllFavoriteMovie(): LiveData<List<FavoriteMovie>> = mFavoriteMovieDao.getAllFavMovies()

    fun getFavMovieById(id: String): LiveData<List<FavoriteMovie>> = mFavoriteMovieDao.getFavMovieById(id)

    fun insertFavoriteMovie(favoriteMovie: FavoriteMovie) {
        executorService.execute { mFavoriteMovieDao.insert(favoriteMovie) }
    }

    fun deleteFavoriteMovie(favoriteMovie: FavoriteMovie) {
        executorService.execute { mFavoriteMovieDao.delete(favoriteMovie) }
    }

    companion object {
        @Volatile
        private var instance: MoviesRespository? = null

        fun getInstance(application: Application): MoviesRespository =
            instance ?: synchronized(this) {
                MoviesRespository(application).apply {
                    instance = this
                }
            }
    }
}