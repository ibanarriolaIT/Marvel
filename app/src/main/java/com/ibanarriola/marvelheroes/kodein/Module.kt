package com.ibanarriola.marvelheroes.kodein

import com.ibanarriola.marvelheroes.data.repository.HeroesRepository
import com.ibanarriola.marvelheroes.data.datasource.ApiDataSource
import com.ibanarriola.marvelheroes.data.datasource.DataModule
import com.ibanarriola.marvelheroes.data.usecase.GetHeroes
import com.ibanarriola.marvelheroes.view.viewmodel.MainViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

val heroesRepositoryModel = Kodein.Module("heroesApp") {
    bind<HeroesRepository>() with singleton {
        HeroesRepository(instance())
    }

    bind<ApiDataSource>() with singleton {
        DataModule.create()
    }

    bind<GetHeroes>() with singleton {
        GetHeroes(instance())
    }

    bind<MainViewModelFactory>() with provider {
        MainViewModelFactory(instance())
    }


}

