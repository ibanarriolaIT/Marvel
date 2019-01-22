package com.ibanarriola.marvelheroes.view.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.github.salomonbrys.kodein.instance
import com.ibanarriola.marvelheroes.kodein.heroesRepositoryModel
import com.ibanarriola.marvelheroes.repository.HeroesRepository
import com.ibanarriola.marvelheroes.repository.model.Heroes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.HttpException
import kotlin.coroutines.CoroutineContext

class MainViewModel(private val uiContext: CoroutineContext = Dispatchers.Main) : ViewModel(), CoroutineScope {

    private val heroesRepository: HeroesRepository = heroesRepositoryModel.instance()
    val data = MutableLiveData<List<Heroes.Hero>>()

    private var job: Job = Job()
    override val coroutineContext: CoroutineContext
        get() = uiContext + job

    fun getHeroesFromRepository(page: Int) {
        launch {
            try {
                val response = heroesRepository.getHeroes(page).await()
                data.value = response.data.results
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