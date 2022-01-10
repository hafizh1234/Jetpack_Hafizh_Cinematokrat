package com.projek.submissionjetpack1.ui.tvShow

import com.projek.submissionjetpack1.data.source.local.entity.FilmEntity

interface TvShowCallback {
    fun shareTvShow(filmEntity: FilmEntity)
    fun navigateToDetail(filmEntity: FilmEntity)
}
