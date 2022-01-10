package com.projek.submissionjetpack1.ui.detailFilm

import com.projek.submissionjetpack1.data.source.local.entity.Casting

interface ActorCallback {
    fun castingClicked(caster: Casting)
}