package com.projek.submissionjetpack1.data.source.remote.retrofit

import com.projek.submissionjetpack1.data.source.remote.response.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    //interface for preparing an object to send retrofit request to the server
    //preparing for getting the casters
    @GET("person/{id}")
    fun getCaster(
        @Path("id") id: Int,
        @Query("api_key") apiKey: String = "572e699880dde7c6ad7dd7f54412b075"
    ): Call<CasterResponse>

    //preparing for getting the detailed tv Show
    @GET("tv/{id}")
    fun getTvShowDetailedInfo(
        @Path("id") id: Int,
        @Query("api_key") apiKey: String = "572e699880dde7c6ad7dd7f54412b075",
        @Query("append_to_response") appendToResponse: String = "aggregate_credits"
    ): Call<DetailTvShow>

    //preparing for getting detailed movie
    @GET("movie/{id}")
    fun getDetailedMovieInfo(
        @Path("id") id: Int,
        @Query("api_key") apiKey: String = "572e699880dde7c6ad7dd7f54412b075",
        @Query("append_to_response") appendToResponse: String = "credits"
    ): Call<DetailMovie>

    //get all genre from movie
    @GET("genre/movie/list")
    fun getMovieGenres(
        @Query("api_key") apiKey: String = "572e699880dde7c6ad7dd7f54412b075",
        @Query("language") language: String = "en-US"
    ): Call<Genres>

    //get all genre from tv
    @GET("genre/tv/list")
    fun getTvShowListGenre(
        @Query("api_key") apiKey: String = "572e699880dde7c6ad7dd7f54412b075",
        @Query("language") language: String = "en-US"
    ): Call<Genres>

    //get all list i prepared in my account in tmdb:
    //7112354 = list TvShow
    //7112336 =list movie
    @GET("list/{id}")
    fun getListMovie(
        @Path("id") id:String,
        @Query("api_key") apiKey: String = "572e699880dde7c6ad7dd7f54412b075"
    ):Call<ListMovie>

    @GET("list/{id}")
    fun getListTvShow(
        @Path("id") id:String,
        @Query("api_key") apiKey: String = "572e699880dde7c6ad7dd7f54412b075"
    ):Call<ListTvShow>

    @GET("tv/{id}/aggregate_credits")
    fun getAllCrew(
        @Path("id") id:String,
        @Query("api_key") apiKey:String="572e699880dde7c6ad7dd7f54412b075",
        @Query("language") language:String="en-US"
    ):Call<Credits>
}