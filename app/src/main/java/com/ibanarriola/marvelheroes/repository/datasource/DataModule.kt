package com.ibanarriola.marvelheroes.repository.datasource

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object DataModule {
    const val BASE_URL = "https://gateway.marvel.com/v1/"

    fun create(): ApiDataSource {
        val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(
                        CoroutineCallAdapterFactory())
                .addConverterFactory(
                        MoshiConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
        return retrofit.create(ApiDataSource::class.java)
    }
}