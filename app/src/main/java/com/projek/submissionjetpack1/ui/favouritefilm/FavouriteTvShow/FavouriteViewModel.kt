package com.projek.submissionjetpack1.ui.favouritefilm.FavouriteTvShow

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.projek.submissionjetpack1.data.source.FilmRepository
import com.projek.submissionjetpack1.data.source.local.entity.FilmEntity

class FavouriteViewModel(private val filmRepository: FilmRepository): ViewModel() {
    fun getTvShows(): LiveData<List<FilmEntity>> {
        return filmRepository.getFavouritedFilm()
    }
}