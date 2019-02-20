package com.ibanarriola.marvelheroes.view.presenter

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import com.github.salomonbrys.kodein.instance
import com.ibanarriola.marvelheroes.kodein.heroesRepositoryModel
import com.ibanarriola.marvelheroes.data.datasource.State
import com.ibanarriola.marvelheroes.data.datasource.heroes.HeroesDataSource
import com.ibanarriola.marvelheroes.data.datasource.heroes.HeroesDataSourceFactory
import com.ibanarriola.marvelheroes.data.model.Heroes
import io.reactivex.disposables.CompositeDisposable

class HeroesListViewModel : ViewModel() {

    var heroesList: LiveData<PagedList<Heroes.MapHero>>
    private val pageSize = 20
    private val compositeDisposable: CompositeDisposable = heroesRepositoryModel.instance()
    private val heroesDataSourceFactory: HeroesDataSourceFactory = heroesRepositoryModel.instance()

    init {
        val config = PagedList.Config.Builder()
                .setPageSize(pageSize)
                .setInitialLoadSizeHint(pageSize)
                .setPrefetchDistance(pageSize * 2)
                .setEnablePlaceholders(false)
                .build()
        heroesList = LivePagedListBuilder<Int, Heroes.MapHero>(heroesDataSourceFactory, config).build()
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