package com.ibanarriola.marvelheroes.data.datasource

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

object DataModule {
    fun create(): ApiDataSource {

        val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(
                        RxJava2CallAdapterFactory.create())
                .addConverterFactory(
                        MoshiConverterFactory.create())
                .baseUrl("https://gateway.marvel.com/v1/")
                .build()

        return retrofit.create(ApiDataSource::class.java)
    }
}