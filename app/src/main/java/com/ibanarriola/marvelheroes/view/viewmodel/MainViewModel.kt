package com.ibanarriola.marvelheroes.view.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ibanarriola.marvelheroes.data.repository.HeroesRepository
import com.ibanarriola.marvelheroes.data.model.Heroes
import com.ibanarriola.marvelheroes.data.usecase.GetHeroes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.HttpException
import kotlin.coroutines.CoroutineContext

class MainViewModel(private val getHeroes: GetHeroes, private val uiContext: CoroutineContext = Dispatchers.Main) : ViewModel(), CoroutineScope {

    val data = MutableLiveData<List<Heroes.MapHero>>()

    private var job: Job = Job()
    override val coroutineContext: CoroutineContext
        get() = uiContext + job

    fun getHeroesFromRepository(page: Int) {
        launch {
            try {
                val response = getHeroes.execute(page)
                data.value = response
            } catch (e: HttpException) {
                data.value = null
            } catch (e: Throwable) {
                data.value = null
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}