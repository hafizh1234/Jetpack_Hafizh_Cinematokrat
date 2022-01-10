package com.projek.submissionjetpack1.ui.caster

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.projek.submissionjetpack1.data.source.local.entity.Casting
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
class CasterViewModelTest {
    private var film=DataDummy.getMovies()
    private var actor = DataDummy.getActor()[0]
    private var director = DataDummy.getDirector()[0]
    private lateinit var viewModel: CasterViewModel

    @Mock
    private lateinit var filmRepository: FilmRepository

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun set() {
        viewModel = CasterViewModel(filmRepository)
    }

    @Mock
    private lateinit var observerActor: Observer<Resource<Casting>>

    @Mock
    private lateinit var observerDirector:Observer<Resource<Casting>>

    @Test
    fun testGetActor() {
        viewModel.initialize(actor.idCaster,actor.idFilm)
        val dummyActor=Resource.success(actor)
        val actorPicked=MutableLiveData<Resource<Casting>>()
        actorPicked.value=dummyActor
        `when`(filmRepository.getDetailedCaster(actor.idCaster.toInt(),actor.idFilm)).thenReturn(actorPicked)
        /*
        val actorChosen=viewModel.getCaster().value as Casting
        assertNotNull(actorChosen)
        assertEquals(actorChosen.idCaster,actor.idCaster)
        assertEquals(actorChosen.nameCaster,actor.nameCaster)

         */
        viewModel.getCaster().observeForever(observerActor)
        verify(observerActor).onChanged(dummyActor)
    }

    @Test
    fun testGetDirector() {
        viewModel.initialize(director.idCaster,actor.idFilm)

        val dummyDirector=Resource.success(director)
        val directorPicked=MutableLiveData<Resource<Casting>>()
        directorPicked.value=dummyDirector
        `when`(filmRepository.getDetailedCaster(director.idCaster.toInt(),director.idFilm)).thenReturn(directorPicked)
        /*val directorChosen=viewModel.getCaster().value as Casting
        assertNotNull(directorChosen)
        assertEquals(directorChosen.idCaster,directorChosen.idCaster)
        assertEquals(directorChosen.nameCaster,directorChosen.nameCaster)

         */
        viewModel.getCaster().observeForever(observerDirector)
        verify(observerDirector).onChanged(dummyDirector)
    }

}