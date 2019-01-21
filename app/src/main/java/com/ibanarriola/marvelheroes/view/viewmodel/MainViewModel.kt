package com.ibanarriola.marvelheroes.view.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.github.salomonbrys.kodein.instance
import com.ibanarriola.marvelheroes.kodein.heroesRepositoryModel
import com.ibanarriola.marvelheroes.repository.HeroesRepository
import com.ibanarriola.marvelheroes.repository.model.Heroes

class MainViewModel : ViewModel() {

    private val heroesRepository: HeroesRepository = heroesRepositoryModel.instance()

    fun getHeroesFromRepository(page: Int): LiveData<Heroes.DataResult> {
        return heroesRepository.getHeroes(page)
    }
}