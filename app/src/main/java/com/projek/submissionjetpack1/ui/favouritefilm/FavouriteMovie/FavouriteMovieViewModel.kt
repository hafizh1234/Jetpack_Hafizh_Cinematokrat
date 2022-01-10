package com.projek.submissionjetpack1.ui.favouritefilm.FavouriteMovie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.projek.submissionjetpack1.data.source.FilmRepository
import com.projek.submissionjetpack1.data.source.local.entity.FilmEntity

class FavouriteMovieViewModel(private val filmRepository: FilmRepository): ViewModel() {
    fun getMovieFavourite(): LiveData<List<FilmEntity>> {
        return filmRepository.getFavouritedFilm()
    }
}