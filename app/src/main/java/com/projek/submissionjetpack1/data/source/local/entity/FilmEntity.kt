package com.projek.submissionjetpack1.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

//FilmEntity mempunyai primary key idFilm untuk refernya casting
@Entity(tableName="filmentities")
data class FilmEntity(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name="idFilm")
    var idFilm: String,

    @ColumnInfo(name="title")
    var title: String,

    @ColumnInfo(name="rating")
    var rating: String,

    @ColumnInfo(name="releaseDate")
    var releaseDate: String,

    @ColumnInfo(name="description")
    var description: String,

    @ColumnInfo(name="imageMovie")
    var imageMovie: String,

    @ColumnInfo(name="genres")
    var genres: String,

    @ColumnInfo(name="filmType")
    var filmType:String,

    @ColumnInfo(name="favourited")
    var favourited:Boolean=false
    //filmType=movie||tvShow
)