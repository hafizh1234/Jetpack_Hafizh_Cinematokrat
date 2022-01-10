package com.projek.submissionjetpack1.data.source.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.load.engine.Resource
import com.projek.submissionjetpack1.data.source.ApiResponse
import com.projek.submissionjetpack1.data.source.remote.response.*
import com.projek.submissionjetpack1.data.source.remote.retrofit.ApiService
import com.projek.submissionjetpack1.data.source.remote.retrofit.RetrofitBuilder
import com.projek.submissionjetpack1.utils.EspressoIdlingResources
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteDataSource {
    companion object {
        @Volatile
        private var instance: RemoteDataSource? = null
        fun getInstance(): RemoteDataSource =
            instance ?: synchronized(this) {
                instance ?: RemoteDataSource().apply {
                    instance = this
                }
            }
        //implementing volatile object for making remoteDataSource just have 1 instance
    }

    //must implementing callback for retreiving the asynchronous retrofit call
    //callback will waiting for the retrofit to get http response on the repository
    fun getMovieList(): LiveData<ApiResponse<List<MovieList>>> {
        val resultMovieList=MutableLiveData<ApiResponse<List<MovieList>>>()
        EspressoIdlingResources.increment()
        val apiService: ApiService = RetrofitBuilder.getService()
        apiService.getListMovie("7112336")
            .enqueue(object : Callback<ListMovie> {
                override fun onResponse(call: Call<ListMovie>, response: Response<ListMovie>) {
                    var listMovie= response.body()?.items
                    if(listMovie!=null) {
                        resultMovieList.value = ApiResponse.succes(listMovie)
                    }
                    EspressoIdlingResources.decrement()
                }
                override fun onFailure(call: Call<ListMovie>, t: Throwable) {

                }

            })
        return resultMovieList
    }

    interface OnLoadMovies {
        fun onAllMoviesListReceived(movieList: ListMovie)
    }

    fun getTvShowList():LiveData<ApiResponse<List<TvShowList>>>{
        val tvShowLive=MutableLiveData<ApiResponse<List<TvShowList>>>()
        EspressoIdlingResources.increment()
        val apiService = RetrofitBuilder.getService()
        apiService.getListTvShow("7112354")
            .enqueue(object : Callback<ListTvShow> {
                override fun onResponse(call: Call<ListTvShow>, response: Response<ListTvShow>) {
                    var listTvShow=response.body()?.items
                    listTvShow?.let { tvShowLive.value=ApiResponse.succes(it) }
                    EspressoIdlingResources.decrement()
                }

                override fun onFailure(call: Call<ListTvShow>, t: Throwable) {

                }

            })
        return tvShowLive
    }

    interface OnLoadTvShowCallback {
        fun onAllTvShowReceived(tvShowList: ListTvShow)
    }

    fun getDetailedMovie(idMovie: Int):LiveData<ApiResponse<DetailMovie>> {
        val movie=MutableLiveData<ApiResponse<DetailMovie>>()
        EspressoIdlingResources.increment()
        val apiService = RetrofitBuilder.getService()
        apiService.getDetailedMovieInfo(idMovie)
            .enqueue(object : Callback<DetailMovie> {
                override fun onResponse(call: Call<DetailMovie>, response: Response<DetailMovie>) {
                    var movieResult=response.body()
                    if(movieResult!=null){
                        movie.value=ApiResponse.succes(movieResult)
                    }
                    EspressoIdlingResources.decrement()
                }

                override fun onFailure(call: Call<DetailMovie>, t: Throwable) {

                }

            })
        return movie
    }

    interface OnLoadDetailedMovieCallback {
        fun onDetailedMovieReceivedResponse(detailMovie: DetailMovie)
    }

    fun getDetailedTvShowRetrofit(idTvShow: Int):LiveData<ApiResponse<DetailTvShow>> {
        val tvShow=MutableLiveData<ApiResponse<DetailTvShow>>()
        EspressoIdlingResources.increment()
        val apiService = RetrofitBuilder.getService()
        apiService.getTvShowDetailedInfo(idTvShow)
            .enqueue(object : Callback<DetailTvShow> {
                override fun onResponse(
                    call: Call<DetailTvShow>,
                    response: Response<DetailTvShow>
                ) {
                    val detailTvShow=response.body()
                    if(detailTvShow!=null){
                        tvShow.value=ApiResponse.succes(detailTvShow)
                    }
                    EspressoIdlingResources.decrement()
                }

                override fun onFailure(call: Call<DetailTvShow>, t: Throwable) {
                }

            })
        return tvShow
    }

    interface OnLoadDetailedTvShowCallback {
        fun onDetailedTvShowReceived(detailTvShow: DetailTvShow)
    }

    fun getGenresMovie():LiveData<ApiResponse<List<GenresItem>>>{
        val genreMovies=MutableLiveData<ApiResponse<List<GenresItem>>>()
        EspressoIdlingResources.increment()
        val apiService = RetrofitBuilder.getService()
        apiService.getMovieGenres()
            .enqueue(object : Callback<Genres> {
                override fun onResponse(call: Call<Genres>, response: Response<Genres>) {
                    val genres=response.body()
                    if(genres!=null){
                        genreMovies.value=ApiResponse.succes(genres.genres)
                    }
                       EspressoIdlingResources.decrement()
                }

                override fun onFailure(call: Call<Genres>, t: Throwable) {

                }

            })

        return genreMovies
    }

    interface OnAllGenreMovieCallback {
        fun onAllGenresMovieReceived(genres: Genres)
    }

    fun getGenresTvShow():LiveData<ApiResponse<List<GenresItem>>> {
        val genresTv=MutableLiveData<ApiResponse<List<GenresItem>>>()
        EspressoIdlingResources.increment()
        val apiService = RetrofitBuilder.getService()
        apiService.getTvShowListGenre()
            .enqueue(object : Callback<Genres> {
                override fun onResponse(call: Call<Genres>, response: Response<Genres>) {
                    val genres=response.body()
                    if(genres!=null) {
                        genresTv.value = ApiResponse.succes(genres.genres)
                    }
                    EspressoIdlingResources.decrement()
                }

                override fun onFailure(call: Call<Genres>, t: Throwable) {

                }

            })

        return genresTv
    }

    interface OnAllGenresTvShow {
        fun onGenresReceived(genres: Genres)
    }

    fun getDetailCaster(idCaster: Int):LiveData<ApiResponse<CasterResponse>> {
        val detailCaster=MutableLiveData<ApiResponse<CasterResponse>>()
        EspressoIdlingResources.increment()
        val apiService = RetrofitBuilder.getService()
        apiService.getCaster(idCaster)
            .enqueue(object : Callback<CasterResponse> {
                override fun onResponse(
                    call: Call<CasterResponse>,
                    response: Response<CasterResponse>
                ) {
                    val casterResult=response.body()
                    if(casterResult!=null){
                        detailCaster.value=ApiResponse.succes(casterResult)
                    }
                    EspressoIdlingResources.decrement()
                }

                override fun onFailure(call: Call<CasterResponse>, t: Throwable) {

                }

            })
        return detailCaster
    }

    interface OnLoadDetailCasterCallback {
        fun onCasterReceived(caster: CasterResponse)
    }

    fun getAllTvShowCaster(tvShowId: String):LiveData<ApiResponse<Credits>>{
        val allTvShowCaster=MutableLiveData<ApiResponse<Credits>>()
        EspressoIdlingResources.increment()
        val apiService = RetrofitBuilder.getService()
        apiService.getAllCrew(tvShowId)
            .enqueue(object : Callback<Credits> {
                override fun onResponse(call: Call<Credits>, response: Response<Credits>) {
                    var responseTvShow=response.body()
                    if(responseTvShow!=null){
                        allTvShowCaster.value=ApiResponse.succes(responseTvShow)
                    }

                    EspressoIdlingResources.decrement()
                }

                override fun onFailure(call: Call<Credits>, t: Throwable) {

                }

            })
        return allTvShowCaster
    }

    interface OnLoadAllCrewCallback {
        fun onCrewsReceive(credits: Credits)
    }
}