package com.ibanarriola.marvelheroes

import com.ibanarriola.marvelheroes.repository.HeroesRepository
import com.ibanarriola.marvelheroes.repository.model.Heroes
import com.ibanarriola.marvelheroes.view.activity.ActivityStatesListener
import com.ibanarriola.marvelheroes.view.presenter.MainPresenter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import java.util.*


class HeroesDataSourceTest {

    @Mock
    lateinit var heroesRepository: HeroesRepository
    @Mock
    lateinit var activityListener: ActivityStatesListener

    val hero = Heroes.Hero(1, "superman", "holasuperman", 1, null, null)
    val results = Arrays.asList(hero)
    val data = Heroes.Data(results)
    val dataResult = Heroes.DataResult(data)



    @Before
    fun initTest() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun testLoadInitialSuccess() = runBlocking {
        `when`(heroesRepository.getHeroes(0)).thenReturn(dataResult)
        val mainPresenter = MainPresenter(Dispatchers.IO).apply {
            setActivityListener(activityListener)
            getHeroesFromRepository(0)
        }
    }
}