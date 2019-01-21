package com.ibanarriola.marvelheroes

import android.arch.lifecycle.LiveData
import com.ibanarriola.marvelheroes.repository.HeroesRepository
import com.ibanarriola.marvelheroes.repository.model.Heroes
import com.ibanarriola.marvelheroes.view.viewmodel.MainViewModel
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import java.util.*


class HeroesDataSourceTest {

    @Mock
    lateinit var heroesRepository: HeroesRepository

    @Mock
    lateinit var liveData: LiveData<Heroes.DataResult>
    val hero = Heroes.Hero(1, "superman", "holasuperman", 1, null, null)
    val results = Arrays.asList(hero)
    val data = Heroes.Data(results)


    @Before
    fun initTest() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun testLoadInitialSuccess() {
        `when`(heroesRepository.getHeroes(0)).thenReturn(liveData)
        MainViewModel().getHeroesFromRepository(0)
    }

}