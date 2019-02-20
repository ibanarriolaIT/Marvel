package com.ibanarriola.marvelheroes.data.usecase

import com.ibanarriola.marvelheroes.data.model.Heroes
import com.ibanarriola.marvelheroes.data.repository.HeroesRepository

class GetHeroes(val heroesRepository: HeroesRepository) {

    suspend fun execute(page: Int): List<Heroes.MapHero> {
        val heroes = heroesRepository.getHeroes(page).await()
        return heroes.data.results.map { it.convertToMapHero() }
    }
}