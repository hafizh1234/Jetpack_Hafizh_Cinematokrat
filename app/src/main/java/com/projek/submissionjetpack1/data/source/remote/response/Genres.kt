package com.projek.submissionjetpack1.data.source.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Genres(

    @field:SerializedName("genres")
    val genres: ArrayList<GenresItem>
) : Parcelable

@Parcelize
data class GenresItem(

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("id")
    val id: Int
) : Parcelable
