package com.ibanarriola.marvelheroes.kodein

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.provider
import com.github.salomonbrys.kodein.singleton
import com.ibanarriola.marvelheroes.repository.HeroesRepository
import com.ibanarriola.marvelheroes.repository.datasource.ApiDataSource
import com.ibanarriola.marvelheroes.repository.datasource.DataModule
import com.ibanarriola.marvelheroes.view.viewmodel.MainViewModel

val heroesRepositoryModel = Kodein {
    bind<HeroesRepository>() with singleton {
        HeroesRepository()
    }

    bind<ApiDataSource>() with singleton {
        DataModule.create()
    }

    bind<MainViewModel>() with provider {
        MainViewModel()
    }


}

