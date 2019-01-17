package com.ibanarriola.testapplication.view.presenter

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import com.ibanarriola.testapplication.repository.HeroesRepository
import com.ibanarriola.testapplication.repository.datasource.heroes.HeroesDataSource
import com.ibanarriola.testapplication.repository.datasource.heroes.HeroesDataSourceFactory
import com.ibanarriola.testapplication.repository.model.Heroes
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class MainPresenter @Inject constructor() : ViewModel() {

    @Inject
    lateinit var heroesRepository: HeroesRepository
    lateinit var heroesList: LiveData<PagedList<Heroes.Hero>>
    private val compositeDisposable = CompositeDisposable()
    private val pageSize = 20
    private lateinit var heroesDataSourceFactory: HeroesDataSourceFactory

    fun findHeroes() {
        heroesDataSourceFactory = HeroesDataSourceFactory(compositeDisposable, heroesRepository)
        val config = PagedList.Config.Builder()
                .setPageSize(pageSize)
                .setInitialLoadSizeHint(pageSize * 2)
                .setEnablePlaceholders(false)
                .build()
        heroesList = LivePagedListBuilder<Int, Heroes.Hero>(heroesDataSourceFactory, config).build()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}