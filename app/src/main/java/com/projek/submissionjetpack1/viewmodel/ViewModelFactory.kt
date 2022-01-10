package com.projek.submissionjetpack1.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.projek.submissionjetpack1.data.source.FilmRepository
import com.projek.submissionjetpack1.di.Injection
import com.projek.submissionjetpack1.ui.caster.CasterViewModel
import com.projek.submissionjetpack1.ui.detailFilm.DetailFilmViewModel
import com.projek.submissionjetpack1.ui.favouritefilm.FavouriteMovie.FavouriteMovieViewModel
import com.projek.submissionjetpack1.ui.favouritefilm.FavouriteTvShow.FavTvAdapter
import com.projek.submissionjetpack1.ui.favouritefilm.FavouriteTvShow.FavouriteViewModel
import com.projek.submissionjetpack1.ui.movies.MovieViewModel
import com.projek.submissionjetpack1.ui.tvShow.TvShowViewModel

class ViewModelFactory private constructor(private val filmRepository: FilmRepository) :
    ViewModelProvider.NewInstanceFactory() {

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideFilmRepository(context)).apply {
                    instance = this
                }
            }
    }
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        when{
            modelClass.isAssignableFrom(CasterViewModel::class.java)->{
                return CasterViewModel(filmRepository) as T
            }
            modelClass.isAssignableFrom(DetailFilmViewModel::class.java)->{
                return DetailFilmViewModel(filmRepository) as T
            }
            modelClass.isAssignableFrom(MovieViewModel::class.java)->{
                return MovieViewModel(filmRepository) as T
            }
            modelClass.isAssignableFrom(TvShowViewModel::class.java)->{
                return TvShowViewModel(filmRepository) as T
            }
            modelClass.isAssignableFrom(FavouriteViewModel::class.java)->{
                return FavouriteViewModel(filmRepository) as T
            }
            modelClass.isAssignableFrom(FavouriteMovieViewModel::class.java)->{
                return FavouriteMovieViewModel(filmRepository) as T
            }
            else->{
                throw Throwable("Unknown ViewModel Class :${modelClass.name}")
            }
        }
    }
}