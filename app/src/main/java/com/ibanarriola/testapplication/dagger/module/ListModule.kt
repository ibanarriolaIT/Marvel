package com.ibanarriola.testapplication.dagger.module

import com.ibanarriola.testapplication.dagger.PerActivity
import com.ibanarriola.testapplication.repository.HeroesRepository
import com.ibanarriola.testapplication.view.presenter.MainPresenter
import dagger.Module
import dagger.Provides

@Module
class ListModule {
    @Provides
    @PerActivity
    fun providesMainPresenter(): MainPresenter = MainPresenter()

    @Provides
    fun providesHeroesRepository(): HeroesRepository = HeroesRepository()

}