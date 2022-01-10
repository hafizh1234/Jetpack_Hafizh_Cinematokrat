package com.projek.submissionjetpack1.data.source.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Credits(
    @field:SerializedName("cast")
    val cast: ArrayList<CasterResponse>,
    @field:SerializedName("crew")
    val crew: ArrayList<CrewItem>
) : Parcelable
