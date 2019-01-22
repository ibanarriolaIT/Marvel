package com.ibanarriola.marvelheroes.kodein

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.provider
import com.ibanarriola.marvelheroes.repository.HeroesRepository
import com.ibanarriola.marvelheroes.repository.datasource.ApiDataSource
import com.ibanarriola.marvelheroes.repository.datasource.DataModule
import com.ibanarriola.marvelheroes.view.viewmodel.MainViewModel

val heroesRepositoryModel = Kodein {
    bind<HeroesRepository>() with provider {
        HeroesRepository()
    }

    bind<ApiDataSource>() with provider {
        DataModule.create()
    }

    bind<MainViewModel>() with provider {
        MainViewModel()
    }


}

