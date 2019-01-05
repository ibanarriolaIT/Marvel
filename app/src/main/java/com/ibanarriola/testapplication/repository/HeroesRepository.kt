package com.ibanarriola.testapplication.repository

import com.ibanarriola.testapplication.repository.datasource.DataModule
import com.ibanarriola.testapplication.repository.model.Heroes
import io.reactivex.Single
import java.security.MessageDigest
import java.util.*
import javax.inject.Inject

class HeroesRepository @Inject constructor() {

    val privateKey = "5009bb73066f50f127907511e70f691cd3f2bb2c"
    val publicKey = "51ef4d355f513641b490a80d32503852"
    val apiDataSource = DataModule.create()
    val pageSize = 20

    fun getHeroes(page: Int): Single<Heroes.DataResult> {
        val now = Date().time.toString()
        val hash = generateHash(now + privateKey + publicKey)
        val offset: Int = page * pageSize
        return apiDataSource.getHeroes(now, publicKey, hash, offset, pageSize)
    }

    fun generateHash(variable: String): String {
        val md = MessageDigest.getInstance("MD5")
        val digested = md.digest(variable.toByteArray())
        return digested.joinToString("") {
            String.format("%02x", it)
        }
    }
}