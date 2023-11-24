package com.example.movieslist.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FavoriteMovie::class], version = 1)
abstract class FavoriteMovieDatabase : RoomDatabase() {
    abstract fun favoriteMovie(): FavoriteMovieDao

    companion object {
        @Volatile
        private var INSTANCE: FavoriteMovieDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): FavoriteMovieDatabase {
            if (INSTANCE == null) {
                synchronized(FavoriteMovieDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                    FavoriteMovieDatabase::class.java, "Favorite Movie")
                        .build()
                }
            }
            return INSTANCE as FavoriteMovieDatabase
        }
    }
}