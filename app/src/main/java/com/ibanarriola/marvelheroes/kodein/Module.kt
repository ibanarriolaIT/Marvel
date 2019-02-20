package com.ibanarriola.marvelheroes.kodein

import com.github.salomonbrys.kodein.*
import com.ibanarriola.marvelheroes.data.datasource.heroes.HeroesDataSourceFactory
import com.ibanarriola.marvelheroes.data.repository.HeroesRepository
import com.ibanarriola.marvelheroes.view.presenter.HeroesListViewModel
import io.reactivex.disposables.CompositeDisposable

val heroesRepositoryModel = Kodein {
    bind<HeroesRepository>() with singleton {
        HeroesRepository()
    }

    bind<HeroesListViewModel>() with singleton {
        HeroesListViewModel()
    }

    bind<CompositeDisposable>() with provider {
        CompositeDisposable()
    }

    bind<HeroesDataSourceFactory>() with singleton {
        HeroesDataSourceFactory(instance(), instance())
    }
}

