package com.projek.submissionjetpack1.data.source.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CasterResponse(
    @field:SerializedName("known_for_department")
    val knownForDepartment: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("profile_path")
    val profilePath: String,

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("birthday")
    var birthday: String? = null,

    @field:SerializedName("place_of_birth")
    var placeOfBirth: String? = null,

    @field:SerializedName("biography")
    var biography: String

) : Parcelable