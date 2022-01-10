package com.projek.submissionjetpack1.ui.detailFilm

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.projek.submissionjetpack1.data.source.local.entity.Casting
import com.projek.submissionjetpack1.data.source.local.entity.FilmEntity
import com.projek.submissionjetpack1.data.source.FilmRepository
import com.projek.submissionjetpack1.data.source.local.entity.DetailFilmWithListActor
import com.projek.submissionjetpack1.utils.DataDummy
import com.projek.submissionjetpack1.vo.Resource
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DetailFilmViewModelTest {

    @get:Rule
    var instantTaskexecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var filmRepository: FilmRepository

    private lateinit var viewModel: DetailFilmViewModel
    private var movieChosen = DataDummy.getMovies()[0]
    private var tvShowChosen = DataDummy.getTvShow()[0]

    private lateinit var actorMovie: ArrayList<Casting>
    private lateinit var actorTvShow: ArrayList<Casting>
    private var idMovie = movieChosen.idFilm
    private var idTvShow = tvShowChosen.idFilm

    @Mock
    private lateinit var movieObserver: Observer<Resource<DetailFilmWithListActor>>

    @Mock
    private lateinit var tvShowObserver: Observer<Resource<DetailFilmWithListActor>>

    @Before
    fun set() {
        viewModel = DetailFilmViewModel(filmRepository)

        actorMovie = arrayListOf()
        actorTvShow = arrayListOf()

        val directors = DataDummy.getDirector()
        val actors = DataDummy.getActor()
        /*
        for (director in directors) {
            if (director.idCaster == idDirectorMovie) {
                directorMovie = director
                break
            } else if (director.idCaster == idDirectorTvShow) {
                directorTvShow = director
                break
            }
        }
        for (actor in actors) {
            var actorTvShowPickedChosen = actorShowPicked.firstOrNull { it.idCaster == actor.idCaster }
            var actorMovieChosen = actorMovieChosen.firstOrNull { it.idCaster == actor.idCaster }
            if (actorMovieChosen != null) {
                actorMovie.add(actor)
            } else if (actorTvShowPickedChosen != null) {
                actorTvShow.add(actor)
            }
        }

         */
    }

    @Test
    fun testGetMovieChosen() {
        viewModel.initialize(idMovie, "Movie")
        val dummyMovieChosen= Resource.success(DataDummy.getFilmWithActors(movieChosen,true))

        val movieLiveData = MutableLiveData<Resource<DetailFilmWithListActor>>()
        movieLiveData.value = dummyMovieChosen

        `when`(filmRepository.getDetailMovie(idMovie.toInt())).thenReturn(movieLiveData)
        val movie = viewModel.films.value
        viewModel.films.observeForever(movieObserver)
    /*
        verify(filmRepository).getDetailMovie(idMovie.toInt())
        Assert.assertNotNull(movie)
        Assert.assertEquals(movie.idFilm, idMovie)
        Assert.assertEquals(movie.title, movieChosen.title)
        Assert.assertEquals(movie.releaseDate, movieChosen.releaseDate)
        Assert.assertEquals(movie.rating, movieChosen.rating)
        Assert.assertEquals(movie.idDirector, movieChosen.idDirector)

        for (i in 0 until (movie.filmCast?.size ?: 0)) {
            Assert.assertNotNull(actorTvShow)
            var actor = actorTvShow.firstOrNull { it.idCaster == movie.filmCast?.get(i)?.idCaster }
            if (actor != null) {
                Assert.assertEquals(actor.idCaster, movie.filmCast?.get(i)?.idCaster)
                Assert.assertEquals(actor.nameCaster, movie.filmCast?.get(i)?.nameCaster)
            }
        }


     */
        verify(movieObserver).onChanged(dummyMovieChosen)
    }

    @Test
    fun testGetTvShowChosen() {
        viewModel.initialize(idTvShow, "TvShow")
        val dummyTvShow=Resource.success(DataDummy.getTvShowWithActor(tvShowChosen,true))
        val tvShowLive = MutableLiveData<Resource<DetailFilmWithListActor>>()
        tvShowLive.value = dummyTvShow
        `when`(filmRepository.getTvShowDetailedInfo(idTvShow.toInt())).thenReturn(tvShowLive)

        viewModel.films.observeForever(tvShowObserver)
        /*
        val tvShow = viewModel.getFilmChosen().value as FilmEntity
        verify(filmRepository).getTvShowDetailedInfo(idTvShow.toInt())
        Assert.assertNotNull(tvShow)
        Assert.assertEquals(tvShow.idFilm, idTvShow)
        Assert.assertEquals(tvShow.title, tvShowChosen.title)
        Assert.assertEquals(tvShow.releaseDate, tvShowChosen.releaseDate)
        Assert.assertEquals(tvShow.rating, tvShowChosen.rating)
        Assert.assertEquals(tvShow.idDirector, tvShowChosen.idDirector)

        for (i in 0 until (tvShow.filmCast?.size ?: 0)){
            Assert.assertNotNull(actorTvShow)
            var actor=actorTvShow.firstOrNull{it.idCaster==tvShow.filmCast?.get(i)?.idCaster}
            if(actor!=null){
                Assert.assertEquals(actor.idCaster,tvShow.filmCast?.get(i)?.idCaster)
                Assert.assertEquals(actor.nameCaster,tvShow.filmCast?.get(i)?.nameCaster)
            }
        }

         */

        //viewModel.getFilmChosen().observeForever(tvShowObserver)
        verify(tvShowObserver).onChanged(dummyTvShow)
    }

}