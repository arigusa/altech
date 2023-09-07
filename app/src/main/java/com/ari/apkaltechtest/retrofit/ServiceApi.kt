package com.ari.apkaltechtest.retrofit

import com.ari.apkaltechtest.ResponseApi
import retrofit2.Call
import retrofit2.http.GET

interface ServiceApi {
    @GET("character")
    fun getApi() : Call<ResponseApi>
}