package com.projek.submissionjetpack1.data.source.local

import androidx.lifecycle.LiveData
import com.projek.submissionjetpack1.data.source.local.entity.Casting
import com.projek.submissionjetpack1.data.source.local.entity.DetailFilmWithListActor
import com.projek.submissionjetpack1.data.source.local.entity.FilmEntity
import com.projek.submissionjetpack1.data.source.local.entity.GenreEntity
import com.projek.submissionjetpack1.data.source.local.room.FilmDao

class LocalDataSource private constructor(private val mFilmDao: FilmDao) {

    companion object {

        private var INSTANCE: LocalDataSource? = null
        fun getInstance(filmDao: FilmDao): LocalDataSource =
            INSTANCE ?: LocalDataSource(filmDao)
    }

    fun getAllFilmEntities(): LiveData<List<FilmEntity>> = mFilmDao.getFilm()

    fun getFavouriteFilm(): LiveData<List<FilmEntity>> = mFilmDao.getFavouritedFilm()

    fun getFilmWithAllActors(filmId: String): LiveData<DetailFilmWithListActor> =
        mFilmDao.getFilmWithActorById(filmId)

    fun insertCasters(casters: List<Casting>) = mFilmDao.insertCastingEntities(casters)
    fun insertFilms(films: List<FilmEntity>) = mFilmDao.insertFilmEntities(films)

    fun insertGenres(genres:List<GenreEntity>) =mFilmDao.insertGenres(genres)

    fun updateFilm(filmEntity:FilmEntity,newState:Boolean){
        filmEntity.favourited=newState
        return mFilmDao.updateFilm(filmEntity)
    }

    fun getCaster(casterId:String):LiveData<Casting> =mFilmDao.getCasterInfo(casterId)

    fun updateCaster(caster:Casting)=mFilmDao.updateCaster(caster)

    fun getGenres():LiveData<List<GenreEntity>> =mFilmDao.getGenres()
}