package com.projek.submissionjetpack1.data.source.local.entity

import androidx.room.Embedded
import androidx.room.Relation

data class DetailFilmWithListActor(
    @Embedded
    var mFilmEntity: FilmEntity,

    @Relation(parentColumn = "idFilm", entityColumn = "idFilm")
    var mCasterEntity: List<Casting>
)