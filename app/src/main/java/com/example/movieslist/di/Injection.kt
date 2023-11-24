package com.example.movieslist.di

import android.app.Application
import com.example.movieslist.data.MoviesRespository


object Injection {
    fun provideRespository(application: Application): MoviesRespository {
        return MoviesRespository.getInstance(application)
    }
}