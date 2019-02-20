package com.ibanarriola.marvelheroes.data.datasource

import com.ibanarriola.marvelheroes.data.model.Heroes
import kotlinx.coroutines.Deferred
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
                  @Query("limit") limit: Int): Deferred<Heroes.DataResult>


}