package com.ari.apkaltechtest.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ConfigApi {

    private val baseUrl = "https://rickandmortyapi.com/api/"

    fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getService(): ServiceApi {
        return getRetrofit().create(ServiceApi::class.java)
    }
}