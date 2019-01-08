package com.ibanarriola.marvelheroes.repository.datasource

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object DataModule {
    fun create(): ApiDataSource {

        val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(
                        RxJava2CallAdapterFactory.create())
                .addConverterFactory(
                        GsonConverterFactory.create())
                .baseUrl("https://gateway.marvel.com/v1/")
                .build()

        return retrofit.create(ApiDataSource::class.java)
    }
}