package com.ibanarriola.testapplication.repository.datasource.heroes

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.DataSource
import com.ibanarriola.testapplication.repository.HeroesRepository
import com.ibanarriola.testapplication.repository.model.Heroes
import io.reactivex.disposables.CompositeDisposable

class HeroesDataSourceFactory(private val compositeDisposable: CompositeDisposable,
                              private val heroesRepository: HeroesRepository)
    : DataSource.Factory<Int, Heroes.Hero>() {

    val heroDataSourceLiveData = MutableLiveData<HeroesDataSource>()

    override fun create(): DataSource<Int, Heroes.Hero> {
        val heroesDataSource = HeroesDataSource(heroesRepository, compositeDisposable)
        heroDataSourceLiveData.postValue(heroesDataSource)
        return heroesDataSource
    }
}