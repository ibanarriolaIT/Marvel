package com.ibanarriola.marvelheroes.repository.datasource

import com.ibanarriola.marvelheroes.repository.model.Heroes
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiDataSource {

    @Headers("Content-Type: application/json")

    @GET("public/comics")
    fun getHeroes(@Query("ts") ts: String,
                  @Query("apikey") apikey: String,
                  @Query("hash") hash: String,
                  @Query("offset") offset: Int,
                  @Query("limit") limit: Int): Single<Heroes.DataResult>


}