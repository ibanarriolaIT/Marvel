package com.ibanarriola.marvelheroes.repository

import com.ibanarriola.marvelheroes.Mockable
import com.ibanarriola.marvelheroes.repository.datasource.DataModule
import com.ibanarriola.marvelheroes.repository.model.Heroes
import kotlinx.coroutines.Deferred
import java.security.MessageDigest
import java.util.*

@Mockable
class HeroesRepository {

    val privateKey = "5009bb73066f50f127907511e70f691cd3f2bb2c"
    val publicKey = "51ef4d355f513641b490a80d32503852"
    val apiDataSource = DataModule.create()
    val pageSize = 20

    suspend fun getHeroes(page: Int): Heroes.DataResult {
        val now = Date().time.toString()
        val hash = generateHash(now + privateKey + publicKey)
        val offset: Int = page * pageSize
        return apiDataSource.getHeroes(now, publicKey, hash, offset, pageSize).await()
    }

    fun generateHash(variable: String): String {
        val md = MessageDigest.getInstance("MD5")
        val digested = md.digest(variable.toByteArray())
        return digested.joinToString("") {
            String.format("%02x", it)
        }
    }
}