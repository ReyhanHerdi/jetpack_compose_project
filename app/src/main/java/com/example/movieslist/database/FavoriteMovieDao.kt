package com.example.movieslist.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavoriteMovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(favoriteMovie: FavoriteMovie)

    @Delete
    fun delete(favoriteMovie: FavoriteMovie)

    @Query("SELECT * FROM FavoriteMovie")
    fun getAllFavMovies(): LiveData<List<FavoriteMovie>>

    @Query("SELECT * FROM FavoriteMovie WHERE id = :id")
    fun getFavMovieById(id: String): LiveData<List<FavoriteMovie>>
}