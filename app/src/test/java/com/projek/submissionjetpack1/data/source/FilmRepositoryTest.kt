package com.projek.submissionjetpack1.data.source

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.nhaarman.mockitokotlin2.*
import com.projek.submissionjetpack1.data.source.local.LocalDataSource
import com.projek.submissionjetpack1.data.source.local.entity.FilmEntity
import com.projek.submissionjetpack1.data.source.remote.RemoteDataSource
import com.projek.submissionjetpack1.data.source.remote.response.*
import com.projek.submissionjetpack1.utils.AppExecutors
import com.projek.submissionjetpack1.utils.DataDummy
import com.projek.submissionjetpack1.utils.LiveDataTest
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

class FilmRepositoryTest {

    @get:Rule
    var instantTaskExecutor = InstantTaskExecutorRule()
    private val appExecutors = mock(AppExecutors::class.java)
    private val local = mock(LocalDataSource::class.java)
    private val remoteDataSource = mock(RemoteDataSource::class.java)
    private val filmRepository = FakeFilmRepository(remoteDataSource, local, appExecutors)

    private val dummyListTvShowsRemote = DataDummy.getAllRemoteTvShow()

    private val detailTvShow = dummyListTvShowsRemote[0]
    private val dummyMoviesRemote = DataDummy.getRemoteMovies()
    private val detailMovies = dummyMoviesRemote[0]

    private val actors = DataDummy.getActorRemote()
    private val actorDummy = actors[0]
    private val directors = DataDummy.getDummyRemoteAllDirector()
    private val directorDummy = directors[0]
    private val movieGenres = DataDummy.getMovieGenre()
    private val tvShowGenres = DataDummy.getTvShowGenreComplete()
/*
    @Test
    fun getDetailMovie() {
        var actorFinal = ArrayList<CasterResponse>()
        actorFinal.add(directorDummy)
        actorFinal.add(actorDummy)
        var credited = Credits(actorFinal, arrayListOf())
        var genres = ArrayList<GenresItem>()
        for (item in detailMovies.genreIds) {
            genres.add(GenresItem("", item))
        }
        val detailMovie = DetailMovie(
            credited,
            genres,
            detailMovies.id,
            detailMovies.overview,
            detailMovies.originalTitle,
            detailMovies.releaseDate,
            detailMovies.voteAverage,
            detailMovies.posterPath
        )
        runBlocking {
            doAnswer { invocation ->
                (invocation.arguments[1] as RemoteDataSource.OnLoadDetailedMovieCallback)
                    .onDetailedMovieReceivedResponse(detailMovie)
                null
            }.`when`(remoteDataSource).getDetailedMovie(eq(detailMovies.id), any())
        }
        val detailMoviesEntity =
            LiveDataTest.getValue(filmRepository.getDetailMovie(detailMovies.id))
        runBlocking {
            verify(remoteDataSource).getDetailedMovie(eq(detailMovies.id), any())
        }
        assertNotNull(detailMoviesEntity)
        assertEquals(detailMovies.id.toString(), detailMoviesEntity.idFilm)
        assertEquals(detailMovie.credits.cast.size, detailMoviesEntity.filmCast?.size)
    }

    @Test
    fun getTvShowDetailedInfo() {
        var actorFinal = ArrayList<CasterResponse>()
        actorFinal.add(directors[1])
        actorFinal.add(actors[1])
        actorFinal.add(actors[2])
        var credited = Credits(actorFinal, arrayListOf())
        var genres = ArrayList<GenresItem>()
        for (item in detailTvShow.genreIds) {
            genres.add(GenresItem("", item))
        }
        val detailTvShowFinal = DetailTvShow(
            credited,
            genres,
            detailTvShow.id,
            detailTvShow.overview,
            detailTvShow.originalName,
            detailTvShow.firstAirDate,
            detailTvShow.voteAverage,
            detailTvShow.posterPath
        )
        runBlocking {
            doAnswer { invocation ->
                (invocation.arguments[1] as RemoteDataSource.OnLoadDetailedTvShowCallback)
                    .onDetailedTvShowReceived(detailTvShowFinal)
                null
            }.`when`(remoteDataSource).getDetailedTvShowRetrofit(eq(detailTvShow.id), any())

            doAnswer { invocation ->
                (invocation.arguments[1] as RemoteDataSource.OnLoadAllCrewCallback)
                    .onCrewsReceive(credited)
                null
            }.`when`(remoteDataSource).getAllTvShowCaster(eq(detailTvShow.id.toString()), any())
        }
        val detailShowEntity =
            LiveDataTest.getValue(filmRepository.getTvShowDetailedInfo(detailTvShow.id))
        runBlocking {
            verify(remoteDataSource).getDetailedTvShowRetrofit(eq(detailTvShow.id), any())
            verify(remoteDataSource).getAllTvShowCaster(eq(detailTvShow.id.toString()), any())
        }
        assertNotNull(detailShowEntity)
        assertEquals(detailTvShow.id.toString(), detailShowEntity.idFilm)
        assertEquals(detailTvShowFinal.credits.cast.size, detailShowEntity.filmCast?.size)
    }

    @Test
    fun getDetailedActor() {
        val dummyActor=MutableLiveData<>
        /*runBlocking {
            doAnswer { invocation ->
                (invocation.arguments[1] as RemoteDataSource.OnLoadDetailCasterCallback)
                    .onCasterReceived(actorDummy)
                null
            }.`when`(remoteDataSource).getDetailCaster(eq(actorDummy.id), any())
        }

         */
        var detailCasterResult =
            LiveDataTest.getValue(filmRepository.getDetailedCaster(actorDummy.id))
        /*runBlocking {
            verify(remoteDataSource).getDetailCaster(eq(actorDummy.id), any())
        }
        */
        assertNotNull(detailCasterResult)
        assertEquals(actorDummy.id.toString(), detailCasterResult.idCaster)
        assertEquals(actorDummy.name, detailCasterResult.nameCaster)
    }

    @Test
    fun getDetailedDirector() {
        runBlocking {
            doAnswer {
                (it.arguments[1] as RemoteDataSource.OnLoadDetailCasterCallback)
                    .onCasterReceived(directorDummy)
                null
            }.`when`(remoteDataSource).getDetailCaster(eq(directorDummy.id), any())
        }
        var detailCasterResult =
            LiveDataTest.getValue(filmRepository.getDetailedCaster(directorDummy.id))
        runBlocking {
            verify(remoteDataSource).getDetailCaster(eq(directorDummy.id), any())
        }
        assertNotNull(detailCasterResult)
        assertEquals(directorDummy.id.toString(), detailCasterResult.idCaster)
        assertEquals(directorDummy.name, detailCasterResult.nameCaster)
    }

 */

    @Test
    fun getListTvShow() {
        val dummyTvShow=MutableLiveData<List<FilmEntity>>()

        `when` {
            local.getAllFilmEntities()
        }.thenReturn {dummyTvShow}
        /*
        runBlocking {
            doAnswer { invocation ->
                (invocation.arguments[0] as RemoteDataSource.OnLoadTvShowCallback)
                    .onAllTvShowReceived(listTvShow)
                null
            }.`when`(remoteDataSource).getTvShowList(any())
            doAnswer{invocation->
                (invocation.arguments[0] as RemoteDataSource.OnAllGenresTvShow)
                    .onGenresReceived(tvShowGenres)
                null
            }.`when`(remoteDataSource).getGenresTvShow(any())
        }

         */
        val tvShowList = LiveDataTest.getValue(filmRepository.getListTvShow())
        verify(local).getAllFilmEntities()
        /*runBlocking {
            verify(remoteDataSource).getTvShowList(any())
            verify(remoteDataSource).getGenresTvShow(any())
        }

         */
        assertNotNull(tvShowList.data)
        tvShowList.data?.size?.let { assertEquals(dummyListTvShowsRemote.size.toLong(), it.toLong()) }
    }

    @Test
    fun getMovieList() {

        val dummyMovies = MutableLiveData<List<FilmEntity>>()
        dummyMovies.value = DataDummy.getMovies()
        `when` {
            local.getAllFilmEntities()
        }.thenReturn { dummyMovies }
        /*
        runBlocking {

            doAnswer { invocation ->
                (invocation.arguments[0] as RemoteDataSource.OnLoadMovies)
                    .onAllMoviesListReceived(listMovieList)
                null
            }.`when`(remoteDataSource).getMovieList(any())

            doAnswer { invocation->
                (invocation.arguments[0] as RemoteDataSource.OnAllGenreMovieCallback)
                    .onAllGenresMovieReceived(movieGenres)
                null
            }.`when`(remoteDataSource).getGenresMovie(any())
        }

         */
        val movieList = LiveDataTest.getValue(filmRepository.getMovieList())
        verify(local).getAllFilmEntities()
        /*
        runBlocking {
            verify(remoteDataSource).getMovieList(any())
            verify(remoteDataSource).getGenresMovie(any())
        }

         */
        assertNotNull(movieList.data)
        movieList.data?.size?.let { assertEquals(dummyMoviesRemote.size.toLong(), it.toLong()) }
    }
}
