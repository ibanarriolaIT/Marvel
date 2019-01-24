package com.ibanarriola.marvelheroes.repository

import android.content.Context
import com.github.salomonbrys.kodein.instance
import com.ibanarriola.marvelheroes.Mockable
import com.ibanarriola.marvelheroes.kodein.heroesRepositoryModel
import com.ibanarriola.marvelheroes.repository.datasource.ApiDataSource
import com.ibanarriola.marvelheroes.repository.model.Heroes
import com.ibanarriola.marvelheroes.utils.generateHash
import kotlinx.coroutines.Deferred
import java.util.*

@Mockable
class HeroesRepository {

    val privateKey = "5009bb73066f50f127907511e70f691cd3f2bb2c"
    val publicKey = "51ef4d355f513641b490a80d32503852"
    val apiDataSource: ApiDataSource = heroesRepositoryModel.instance()
    val pageSize = 20

    suspend fun getHeroes(page: Int): Deferred<Heroes.DataResult> {
        val now = Date().time.toString()
        val hash = (now + privateKey + publicKey).generateHash()
        val offset: Int = page * pageSize
        return apiDataSource.getHeroes(now, publicKey, hash, offset, pageSize)
    }
}