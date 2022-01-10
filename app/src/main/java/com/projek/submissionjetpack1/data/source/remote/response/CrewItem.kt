package com.projek.submissionjetpack1.data.source.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CrewItem(

    @field:SerializedName("known_for_department")
    val knownForDepartment: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("profile_path")
    val profilePath: String,

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("job")
    val job: String
) : Parcelable
