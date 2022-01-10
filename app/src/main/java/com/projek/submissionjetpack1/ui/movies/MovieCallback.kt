package com.projek.submissionjetpack1.ui.movies

import com.projek.submissionjetpack1.data.source.local.entity.FilmEntity

interface MovieCallback{
    fun movieShare(filmEntity: FilmEntity)
    fun navigateToDetail(filmEntity: FilmEntity)
}
