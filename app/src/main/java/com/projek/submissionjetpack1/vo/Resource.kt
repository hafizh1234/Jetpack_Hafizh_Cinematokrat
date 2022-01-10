package com.projek.submissionjetpack1.vo

class Resource<T>(val status:StatusProcced,val data:T?,val message:String?){
    companion object{
        fun<T>success(data:T?):Resource<T> = Resource(StatusProcced.SUCCESS,data,null)
        fun<T>error(msg:String?,data:T):Resource<T> =Resource(StatusProcced.ERROR,data,msg)
        fun<T>loading(data:T?):Resource<T> = Resource(StatusProcced.LOADING,data,null)
    }

}