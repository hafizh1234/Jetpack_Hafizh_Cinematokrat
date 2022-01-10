package com.projek.submissionjetpack1.ui.movies

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.projek.submissionjetpack1.data.source.FilmRepository
import com.projek.submissionjetpack1.data.source.local.entity.FilmEntity
import com.projek.submissionjetpack1.utils.DataDummy
import com.projek.submissionjetpack1.vo.Resource
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MovieViewModelTest {
    private lateinit var viewModel: MovieViewModel

    @get:Rule
    var instantTaskExecutorRule=InstantTaskExecutorRule()

    @Before
    fun setAll() {
        viewModel = MovieViewModel(filmRepository)
    }

    @Mock
    private lateinit var filmRepository: FilmRepository

    @Mock
    private lateinit var observer: Observer<Resource<List<FilmEntity>>>


    @Test
    fun testGetMovie() {
        val dummyMovie=Resource.success(DataDummy.getMovies())
        val movie=MutableLiveData<Resource<List<FilmEntity>>>()
        movie.value=dummyMovie

        `when`(filmRepository.getMovieList()).thenReturn(movie)
        val movieEntities = viewModel.getMovie().value?.data

        verify(filmRepository).getMovieList()
        assertNotNull(movieEntities)
        assertEquals(10, movieEntities?.size)
        viewModel.getMovie().observeForever(observer)
        verify(observer).onChanged(dummyMovie)
    }
}