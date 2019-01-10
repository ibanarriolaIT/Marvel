package com.ibanarriola.marvelheroes.view.presenter

import android.arch.lifecycle.ViewModel
import com.github.salomonbrys.kodein.instance
import com.ibanarriola.marvelheroes.kodein.heroesRepositoryModel
import com.ibanarriola.marvelheroes.repository.HeroesRepository
import com.ibanarriola.marvelheroes.view.activity.ActivityStates
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.HttpException

class MainPresenter : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    private val heroesRepository: HeroesRepository = heroesRepositoryModel.instance()
    private lateinit var activity: ActivityStates

    fun setActivityListener(activity: ActivityStates) {
        this.activity = activity
    }

    fun getHeroesFromRepository(page: Int) {
        activity.loading()
        GlobalScope.launch(Dispatchers.Main) {
            try {
                val response = heroesRepository.getHeroes(page).await()
                if (response.isSuccessful)
                    activity.onHeroesReady(response.body()!!.data.results)
                else
                    activity.onError(response.errorBody().toString())
            } catch (e: HttpException) {
                activity.onError(e.message())
            } catch (e: Throwable) {
                activity.onError(e.message)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}