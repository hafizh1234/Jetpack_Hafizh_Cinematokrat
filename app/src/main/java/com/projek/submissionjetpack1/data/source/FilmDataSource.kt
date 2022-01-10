package com.projek.submissionjetpack1.data.source

import androidx.lifecycle.LiveData
import com.projek.submissionjetpack1.data.source.local.entity.Casting
import com.projek.submissionjetpack1.data.source.local.entity.DetailFilmWithListActor
import com.projek.submissionjetpack1.data.source.local.entity.FilmEntity
import com.projek.submissionjetpack1.data.source.local.entity.GenreEntity
import com.projek.submissionjetpack1.vo.Resource

interface FilmDataSource {
    //mengambil movie detail
    fun getDetailMovie(idMovie: Int): LiveData<Resource<DetailFilmWithListActor>>

    //mengambil tvShow detailed untuk detail tvSHow, sama actor.
    fun getTvShowDetailedInfo(idTvShow: Int): LiveData<Resource<DetailFilmWithListActor>>

    //mengambil detailCaster untuk halaman Casting
    fun getDetailedCaster(idCaster: Int,idFilm:String): LiveData<Resource<Casting>>

    fun filmIdentification(idFilm:Int,filmType:String):LiveData<Resource<DetailFilmWithListActor>>
    //mengambil listTvShow untuk halaman awal
    fun getListTvShow(): LiveData<Resource<List<FilmEntity>>>

    //mengambil listMovie mengisi halaman awal
    fun getMovieList(): LiveData<Resource<List<FilmEntity>>>

    //all genre
    fun getGenresAll(typeFilm:String):LiveData<Resource<List<GenreEntity>>>

    fun getFavouritedFilm():LiveData<List<FilmEntity>>

    fun setFavouriteFilm(film:FilmEntity,state:Boolean)
}