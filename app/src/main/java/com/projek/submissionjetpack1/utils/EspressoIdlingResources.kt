package com.projek.submissionjetpack1.utils

import androidx.test.espresso.IdlingResource
import androidx.test.espresso.idling.CountingIdlingResource

object EspressoIdlingResources {
    private val RESOURCE = "GLOBAL"

    val idlingResource = CountingIdlingResource(RESOURCE)
    fun increment() {
        idlingResource.increment()
    }

    fun decrement() {
        idlingResource.decrement()
    }

}