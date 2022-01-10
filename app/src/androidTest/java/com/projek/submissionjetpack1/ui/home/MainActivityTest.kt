package com.projek.submissionjetpack1.ui.home

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.projek.submissionjetpack1.R
import com.projek.submissionjetpack1.data.source.local.entity.Casting
import com.projek.submissionjetpack1.utils.DataDummy
import com.projek.submissionjetpack1.utils.EspressoIdlingResources
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class MainActivityTest {
    private val dummyTvShow = DataDummy.getTvShow()
    private val dummyMovies = DataDummy.getMovies()
    private val dummyDirector = DataDummy.getDirector()

    @Before
    fun setUp(){
        ActivityScenario.launch(MainActivity::class.java)
        IdlingRegistry.getInstance().register(EspressoIdlingResources.idlingResource)
    }
    @After
    fun tearDown(){
        IdlingRegistry.getInstance().unregister(EspressoIdlingResources.idlingResource)
    }
    @Test
    fun loadMovies() {
        onView(withId(R.id.rv_movie)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_movie)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                dummyMovies.size - 1
            )
        )
    }

    @Test
    fun loadDetailMovie() {
        onView(withId(R.id.rv_movie)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                click()
            )
        )
        onView(withId(R.id.title_film)).check(matches(isDisplayed()))
        onView(withId(R.id.title_film)).check(matches(withText(dummyMovies[0].title)))
        onView(withId(R.id.rating_film)).check(matches(isDisplayed()))
        onView(withId(R.id.rating_film)).check(matches(withText(dummyMovies[0].rating)))
        onView(withId(R.id.release_date)).check(matches(isDisplayed()))
        onView(withId(R.id.release_date)).check(matches(withText(dummyMovies[0].releaseDate)))

        onView(withId(R.id.rv_genre)).check(matches(isDisplayed()))
        var sizeGenre = dummyMovies[0].genres.split(",").size
        onView(withId(R.id.rv_genre)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                sizeGenre - 1
            )
        )
        onView(withId(R.id.rv_actor)).check(matches(isDisplayed()))
        var size = 0
        val actors = dummyMovies[0].filmCast?.size
        if (actors != null) {
            size = if (actors % 2 == 0) {
                actors / 2
            } else {
                actors / 2 + 1
            }
        }
        onView(withId(R.id.rv_actor)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                size
            )
        )
    }

    @Test
    fun loadDetailDirector() {
        onView(withId(R.id.rv_movie)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                click()
            )
        )
        onView(withId(R.id.rv_actor)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                MyViewAction.clickChildViewWithId(R.id.linearLayout)
            )
        )
        val directorId = dummyMovies[0].idDirector
        var directorResult = Casting("", "", "", "", "", "", "")
        for (director in dummyDirector) {
            if (director.idCaster == directorId) {
                directorResult = director
            }
        }
        onView(withId(R.id.caster_name)).check(matches(isDisplayed()))
        onView(withId(R.id.caster_name)).check(matches(withText(directorResult.nameCaster)))
        onView(withId(R.id.date_and_place_of_birth)).check(matches(isDisplayed()))
        onView(withId(R.id.date_and_place_of_birth)).check(matches(withText("${directorResult.placeOfBirth}\n${directorResult.dateOfBirth}")))
    }

    @Test
    fun loadTvShow() {
        onView(withText(R.string.tv_show)).perform(click())
        onView(withId(R.id.rv_tv_show)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_tv_show)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                dummyTvShow.size - 1
            )
        )
    }
}