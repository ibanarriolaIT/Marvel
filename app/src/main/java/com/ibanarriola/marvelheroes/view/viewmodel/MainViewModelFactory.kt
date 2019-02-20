package com.ibanarriola.marvelheroes.view.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ibanarriola.marvelheroes.data.usecase.GetHeroes

class MainViewModelFactory(private val getHeroes: GetHeroes) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(getHeroes) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }


}