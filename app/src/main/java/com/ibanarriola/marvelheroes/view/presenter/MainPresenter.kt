package com.ibanarriola.marvelheroes.view.presenter

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.arch.paging.PagedList
import com.github.salomonbrys.kodein.instance
import com.ibanarriola.marvelheroes.kodein.heroesRepositoryModel
import com.ibanarriola.marvelheroes.repository.HeroesRepository
import com.ibanarriola.marvelheroes.repository.model.Heroes
import com.ibanarriola.marvelheroes.view.activity.ActivityStates
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainPresenter : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    private val heroesRepository: HeroesRepository = heroesRepositoryModel.instance()
    private lateinit var activity: ActivityStates

    fun setActivityListener(activity: ActivityStates) {
        this.activity = activity
    }

    fun getHeroesFromRepository(page: Int) {
        activity.loading()
        heroesRepository.getHeroes(page).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe({ dataResult ->
            activity.onHeroesReady(dataResult.data.results)
        }, { error ->
            activity.onError(error.message)
        })
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}