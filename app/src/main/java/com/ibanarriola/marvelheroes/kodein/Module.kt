package com.ibanarriola.marvelheroes.kodein

import com.github.salomonbrys.kodein.*
import com.ibanarriola.marvelheroes.repository.HeroesRepository
import com.ibanarriola.marvelheroes.view.presenter.MainPresenter

val heroesRepositoryModel = Kodein {
    bind<HeroesRepository>() with provider {
        HeroesRepository()
    }

    bind<MainPresenter>() with provider {
        MainPresenter()
    }
}

