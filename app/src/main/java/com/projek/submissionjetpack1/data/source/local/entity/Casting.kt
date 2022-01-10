package com.projek.submissionjetpack1.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

//casting menyimpan director dan actor jadi, sekali ambil punya director dan actor sama punya idFilm reference
//supaya pas naro detailFilm actor juga ditaruh
@Entity(
    tableName = "castingentities",
    primaryKeys = ["idCaster", "idFilm"],
    foreignKeys = [ForeignKey(
        entity = FilmEntity::class,
        parentColumns = ["idFilm"],
        childColumns = ["idFilm"]
    )],
    indices = [Index(value = ["idCaster"]),
        Index(value = ["idFilm"])]
)
data class Casting(
    @NonNull
    @ColumnInfo(name = "idCaster")
    var idCaster: String,

    @NonNull
    @ColumnInfo(name = "nameCaster")
    var nameCaster: String,

    @ColumnInfo(name = "imageCaster")
    var imageCaster: String,

    @NonNull
    @ColumnInfo(name = "biographyCaster")
    var biographyCaster: String,

    @ColumnInfo(name = "dateOfBirth")
    var dateOfBirth: String,

    @ColumnInfo(name = "placeOfBirth")
    var placeOfBirth: String,

    @ColumnInfo(name = "typeCast")
    var typeCast: String,

    @NonNull
    @ColumnInfo(name = "idFilm")
    var idFilm: String

)