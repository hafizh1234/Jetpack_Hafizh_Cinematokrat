package com.projek.submissionjetpack1.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="genreentities")
data class GenreEntity(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name="idGenre")
    var idGenre:String,
    @ColumnInfo(name="description")
    var description:String
)

