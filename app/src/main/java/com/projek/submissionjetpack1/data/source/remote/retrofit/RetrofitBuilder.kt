package com.projek.submissionjetpack1.data.source.remote.retrofit

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitBuilder {
    companion object{
        private const val BASEURL="https://api.themoviedb.org/3/"
        fun getService():ApiService{
            val loggingInterceptor=HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            val httpClient=OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()
            val retrofit= Retrofit.Builder()
                .baseUrl(BASEURL)
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(ApiService::class.java)
        }
    }
}