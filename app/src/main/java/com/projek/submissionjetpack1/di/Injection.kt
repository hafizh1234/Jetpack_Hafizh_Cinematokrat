package com.projek.submissionjetpack1.di

import android.content.Context
import com.projek.submissionjetpack1.data.source.FilmRepository
import com.projek.submissionjetpack1.data.source.local.LocalDataSource
import com.projek.submissionjetpack1.data.source.local.room.FilmDatabase
import com.projek.submissionjetpack1.data.source.remote.RemoteDataSource
import com.projek.submissionjetpack1.utils.AppExecutors

object Injection {
    fun provideFilmRepository(context: Context):FilmRepository{
        val database=FilmDatabase.getInstance(context)
        val localDataSource=LocalDataSource.getInstance(database.filmDao())
        val remoteDataSource=RemoteDataSource.getInstance()
        val appExecutors=AppExecutors()
        return FilmRepository.getInstance(remoteDataSource,localDataSource,appExecutors)
    }
}