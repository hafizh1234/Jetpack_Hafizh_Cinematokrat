package com.projek.submissionjetpack1.ui.caster

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.projek.submissionjetpack1.data.source.local.entity.Casting
import com.projek.submissionjetpack1.data.source.FilmRepository
import com.projek.submissionjetpack1.vo.Resource

class CasterViewModel(private val filmRepository: FilmRepository) : ViewModel() {
    //getting all the data from repository
    private var idFilm:String=""
    private val actorChosenId= MutableLiveData<String>()

    fun initialize(actorId: String,idFilm:String){
        this.actorChosenId.value = actorId
        this.idFilm=idFilm
    }

    fun getInfo(): ArrayList<String?> {
        return arrayListOf(actorChosenId.value,idFilm)
    }

    fun getCaster(): LiveData<Resource<Casting>> = Transformations.switchMap(actorChosenId) {actors->
        filmRepository.getDetailedCaster(actors.toInt(),idFilm)
    }
}