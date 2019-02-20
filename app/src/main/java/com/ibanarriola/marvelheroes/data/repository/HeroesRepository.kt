package com.ibanarriola.marvelheroes.data.repository

import com.ibanarriola.marvelheroes.Mockable
import com.ibanarriola.marvelheroes.data.datasource.ApiDataSource
import com.ibanarriola.marvelheroes.data.model.Heroes
import com.ibanarriola.marvelheroes.utils.generateHash
import kotlinx.coroutines.Deferred
import java.util.*

@Mockable
class HeroesRepository(val apiDataSource: ApiDataSource) {

    val privateKey = "5009bb73066f50f127907511e70f691cd3f2bb2c"
    val publicKey = "51ef4d355f513641b490a80d32503852"
    val pageSize = 20

    suspend fun getHeroes(page: Int): Deferred<Heroes.DataResult> {
        val now = Date().time.toString()
        val hash = (now + privateKey + publicKey).generateHash()
        val offset: Int = page * pageSize
        return apiDataSource.getHeroes(now, publicKey, hash, offset, pageSize)
    }
}