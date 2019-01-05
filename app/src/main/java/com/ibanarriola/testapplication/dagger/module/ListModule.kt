package com.ibanarriola.testapplication.dagger.module

import com.ibanarriola.testapplication.dagger.PerActivity
import com.ibanarriola.testapplication.repository.HeroesRepository
import com.ibanarriola.testapplication.repository.datasource.heroes.HeroesDataSourceFactory
import com.ibanarriola.testapplication.view.presenter.MainPresenter
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

@Module
class ListModule {
    @Provides
    @PerActivity
    fun providesMainPresenter(): MainPresenter = MainPresenter()

    @Provides
    fun providesHeroesRepository(): HeroesRepository = HeroesRepository()

}