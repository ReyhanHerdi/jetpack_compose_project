package com.example.movieslist.di

import com.example.movieslist.data.MoviesRespository


object Injection {
    fun provideRespository(): MoviesRespository {
        return MoviesRespository.getInstance()
    }
}