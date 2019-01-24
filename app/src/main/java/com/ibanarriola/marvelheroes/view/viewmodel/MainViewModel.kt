package com.ibanarriola.marvelheroes.view.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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
    val data = MutableLiveData<List<Heroes.MapHero>>()

    private var job: Job = Job()
    override val coroutineContext: CoroutineContext
        get() = uiContext + job

    fun getHeroesFromRepository(page: Int) {
        launch {
            try {
                val response = heroesRepository.getHeroes(page).await()
                data.value = response.data.results.map { it.convertToMapHero() }
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