package com.ibanarriola.marvelheroes.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.ibanarriola.marvelheroes.Mockable
import com.ibanarriola.marvelheroes.repository.datasource.DataModule
import com.ibanarriola.marvelheroes.repository.model.Heroes
import kotlinx.coroutines.Deferred
import retrofit2.Call
import java.security.MessageDigest
import retrofit2.Callback
import retrofit2.Response
import java.util.*

@Mockable
class HeroesRepository {

    val privateKey = "5009bb73066f50f127907511e70f691cd3f2bb2c"
    val publicKey = "51ef4d355f513641b490a80d32503852"
    val apiDataSource = DataModule.create()
    val pageSize = 20

    fun getHeroes(page: Int): LiveData<Heroes.DataResult> {
        val data = MutableLiveData<Heroes.DataResult>()
        val now = Date().time.toString()
        val hash = generateHash(now + privateKey + publicKey)
        val offset: Int = page * pageSize
        apiDataSource.getHeroes(now, publicKey, hash, offset, pageSize).enqueue(object: Callback<Heroes.DataResult>{
            override fun onFailure(call: Call<Heroes.DataResult>, t: Throwable) {
                data.value = null
            }

            override fun onResponse(call: Call<Heroes.DataResult>, response: Response<Heroes.DataResult>) {
                data.value = response.body()
            }
        })
        return data
    }

    fun generateHash(variable: String): String {
        val md = MessageDigest.getInstance("MD5")
        val digested = md.digest(variable.toByteArray())
        return digested.joinToString("") {
            String.format("%02x", it)
        }
    }
}