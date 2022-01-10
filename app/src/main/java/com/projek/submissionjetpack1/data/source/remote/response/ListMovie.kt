package com.projek.submissionjetpack1.data.source.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ListMovie(

    @field:SerializedName("item_count")
    val itemCount: Int,

    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("items")
    val items: List<MovieList>
):Parcelable
