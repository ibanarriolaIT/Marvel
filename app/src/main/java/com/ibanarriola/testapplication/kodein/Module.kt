package com.ibanarriola.testapplication.kodein

import com.github.salomonbrys.kodein.*
import com.ibanarriola.testapplication.repository.HeroesRepository
import com.ibanarriola.testapplication.view.presenter.MainPresenter

val heroesRepositoryModel = Kodein {
    bind<HeroesRepository>() with provider {
        HeroesRepository()
    }

    bind<MainPresenter>() with provider {
        MainPresenter()
    }
}

