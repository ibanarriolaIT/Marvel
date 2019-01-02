package com.ibanarriola.testapplication.view.presenter

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import com.github.salomonbrys.kodein.instance
import com.ibanarriola.testapplication.kodein.heroesRepositoryModel
import com.ibanarriola.testapplication.repository.datasource.State
import com.ibanarriola.testapplication.repository.datasource.heroes.HeroesDataSource
import com.ibanarriola.testapplication.repository.datasource.heroes.HeroesDataSourceFactory
import com.ibanarriola.testapplication.repository.model.Heroes
import io.reactivex.disposables.CompositeDisposable

class MainPresenter : ViewModel() {

    var heroesList: LiveData<PagedList<Heroes.Hero>>
    private val compositeDisposable = CompositeDisposable()
    private val pageSize = 20
    private val heroesDataSourceFactory: HeroesDataSourceFactory

    init {
        heroesDataSourceFactory = HeroesDataSourceFactory(compositeDisposable, heroesRepositoryModel.instance())
        val config = PagedList.Config.Builder()
                .setPageSize(pageSize)
                .setInitialLoadSizeHint(pageSize * 2)
                .setEnablePlaceholders(false)
                .build()
        heroesList = LivePagedListBuilder<Int, Heroes.Hero>(heroesDataSourceFactory, config).build()
    }

    fun getState(): LiveData<State> = Transformations.switchMap<HeroesDataSource, State>(
            heroesDataSourceFactory.heroDataSourceLiveData, HeroesDataSource::state)

    fun listIsEmpty(): Boolean {
        return heroesList.value?.isEmpty() ?: true
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}