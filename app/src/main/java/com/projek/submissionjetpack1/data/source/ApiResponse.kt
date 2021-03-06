package com.projek.submissionjetpack1.data.source

import com.projek.submissionjetpack1.data.source.remote.StatusResponse

class ApiResponse<T>(val status:StatusResponse, val body:T, val message:String?) {
    companion object{
        fun<T> succes(body:T):ApiResponse<T> =ApiResponse(StatusResponse.SUCCESS,body,null)
        fun<T> empty(msg:String,body:T):ApiResponse<T> =ApiResponse(StatusResponse.EMPTY,body,msg)
        fun<T> error(msg:String,body:T):ApiResponse<T> =ApiResponse(StatusResponse.ERROR,body,msg)
    }
}