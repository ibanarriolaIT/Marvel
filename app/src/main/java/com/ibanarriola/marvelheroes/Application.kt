package com.ibanarriola.marvelheroes

import android.app.Application
import com.ibanarriola.marvelheroes.kodein.heroesRepositoryModel
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware

class Application : Application(), KodeinAware {

    override val kodein: Kodein = Kodein.lazy {
        import(heroesRepositoryModel)
    }


}