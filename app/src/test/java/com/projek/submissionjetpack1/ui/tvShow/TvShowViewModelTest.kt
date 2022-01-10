package com.projek.submissionjetpack1.ui.tvShow

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.projek.submissionjetpack1.data.source.local.entity.FilmEntity
import com.projek.submissionjetpack1.data.source.FilmRepository
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
class TvShowViewModelTest {

    private lateinit var viewModel: TvShowViewModel

    @get:Rule
    var instantTaskExecutor = InstantTaskExecutorRule()

    @Before
    fun set() {
        viewModel = TvShowViewModel(filmRepository)
    }

    @Mock
    private lateinit var filmRepository: FilmRepository

    @Mock
    private lateinit var observer: Observer<Resource<List<FilmEntity>>>

    @Test
    fun testGetTvShows() {
        val dummyTvShow = Resource.success(DataDummy.getTvShow())
        val tvShows= MutableLiveData<Resource<List<FilmEntity>>>()
        tvShows.value=dummyTvShow

        `when`(filmRepository.getListTvShow()).thenReturn(tvShows)
        val tvShowEntities = viewModel.getTvShows().value?.data

        verify(filmRepository).getListTvShow()
        assertNotNull(tvShowEntities)
        assertEquals(10, tvShowEntities?.size)

        viewModel.getTvShows().observeForever(observer)
        verify(observer).onChanged(dummyTvShow)
    }
}