package com.projek.submissionjetpack1.ui.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.projek.submissionjetpack1.data.source.local.entity.FilmEntity
import com.projek.submissionjetpack1.data.source.FilmRepository
import com.projek.submissionjetpack1.vo.Resource

class MovieViewModel(private val filmRepository: FilmRepository):ViewModel() {
    fun getMovie(): LiveData<Resource<List<FilmEntity>>> {
        return filmRepository.getMovieList()
    }
}