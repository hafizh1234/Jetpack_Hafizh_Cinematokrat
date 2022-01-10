package com.projek.submissionjetpack1.ui.detailFilm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.projek.submissionjetpack1.data.source.FilmRepository
import com.projek.submissionjetpack1.data.source.local.entity.DetailFilmWithListActor
import com.projek.submissionjetpack1.vo.Resource

class DetailFilmViewModel(private val filmRepository: FilmRepository) : ViewModel() {
    private var typeFilmChosen=""
    val idFilm= MutableLiveData<String>()

    var films:LiveData<Resource<DetailFilmWithListActor>> =Transformations.switchMap(idFilm){
        mFilmId->
        filmRepository.filmIdentification(mFilmId.toInt(),typeFilmChosen)
    }

    fun initialize(idFilmPicked: String, typeFilm: String) {
        this.idFilm.value = idFilmPicked
        this.typeFilmChosen = typeFilm
    }

    fun getCurrentInfo(): ArrayList<String?> {
        return arrayListOf(this.idFilm.value, this.typeFilmChosen)
    }
    fun setFavouriteFilm(){
        val filmPicked=films.value
        if(filmPicked!=null){
            val filmEntity=filmPicked.data
            if(filmEntity!=null){
                val filmResult=filmEntity.mFilmEntity
                val newState= !filmResult.favourited
                filmRepository.setFavouriteFilm(filmResult,newState)
            }
        }
    }
}