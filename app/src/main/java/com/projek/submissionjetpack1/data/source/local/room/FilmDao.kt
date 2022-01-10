package com.projek.submissionjetpack1.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.projek.submissionjetpack1.data.source.local.entity.Casting
import com.projek.submissionjetpack1.data.source.local.entity.DetailFilmWithListActor
import com.projek.submissionjetpack1.data.source.local.entity.FilmEntity
import com.projek.submissionjetpack1.data.source.local.entity.GenreEntity

@Dao
interface FilmDao {

    //ambil semua data di database untuk loadFrom
    @Query("SELECT * FROM filmentities")
    fun getFilm():LiveData<List<FilmEntity>>

    //ambil semua item yang sdh favourite
    @Query("SELECT * FROM filmentities WHERE favourited=1")
    fun getFavouritedFilm():LiveData<List<FilmEntity>>

    //ambil filmEntities sama isi actor yang sudah directed
    @Transaction
    @Query("SELECT * FROM filmentities WHERE idFilm=:filmId")
    fun getFilmWithActorById(filmId:String): LiveData<DetailFilmWithListActor>

    //masukkan semua films
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFilmEntities(films:List<FilmEntity>)

    @Update
    fun updateCaster(caster:Casting)
    //update salah satu film sesuai film yang terakhir untuk bookmark,(pakai state)
    @Update
    fun updateFilm(film:FilmEntity)

    //get Caster Info mengawasi isi castingEntities mengambil 1 castingentities
    @Query("SELECT * FROM castingentities WHERE idCaster=:idCaster ")
    fun getCasterInfo(idCaster:String):LiveData<Casting>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCastingEntities(castings:List<Casting>)

    @Query("SELECT * FROM genreentities")
    fun getGenres():LiveData<List<GenreEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGenres(genres:List<GenreEntity>)

}