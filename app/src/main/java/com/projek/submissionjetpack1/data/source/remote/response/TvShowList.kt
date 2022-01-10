package com.projek.submissionjetpack1.data.source.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TvShowList(

    @field:SerializedName("overview")
    val overview: String,

    @field:SerializedName("original_name")
    val originalName: String,

    @field:SerializedName("genre_ids")
    val genreIds: ArrayList<Int>,

    @field:SerializedName("poster_path")
    val posterPath: String,

    @field:SerializedName("media_type")
    val mediaType: String,

    @field:SerializedName("first_air_date")
    val firstAirDate: String,

    @field:SerializedName("vote_average")
    val voteAverage: Double,

    @field:SerializedName("id")
    val id: Int
):Parcelable
