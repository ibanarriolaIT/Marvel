package com.ibanarriola.marvelheroes.view.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ibanarriola.marvelheroes.repository.HeroesRepository

class MainViewModelFactory(private val heroesRepository: HeroesRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(heroesRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }


}