package com.ibanarriola.marvelheroes.view.presenter

import android.arch.lifecycle.ViewModel
import com.github.salomonbrys.kodein.instance
import com.ibanarriola.marvelheroes.kodein.heroesRepositoryModel
import com.ibanarriola.marvelheroes.repository.HeroesRepository
import com.ibanarriola.marvelheroes.view.activity.ActivityStatesListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.HttpException
import kotlin.coroutines.CoroutineContext

class MainPresenter(private val uiContext: CoroutineContext = Dispatchers.Main) : ViewModel(), CoroutineScope {

    private val heroesRepository: HeroesRepository = heroesRepositoryModel.instance()
    private lateinit var listener: ActivityStatesListener

    private var job: Job = Job()
    override val coroutineContext: CoroutineContext
        get() = uiContext + job

    fun setActivityListener(listener: ActivityStatesListener) {
        this.listener = listener
    }

    fun getHeroesFromRepository(page: Int) {
        launch {
            try {
                val response = heroesRepository.getHeroes(page).await()
                listener.onHeroesReady(response.data.results)
            } catch (e: HttpException) {
                listener.onError(e.message())
            } catch (e: Throwable) {
                listener.onError(e.message)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}