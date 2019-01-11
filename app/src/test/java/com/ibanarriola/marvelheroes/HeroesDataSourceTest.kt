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

    val heroesRepository: HeroesRepository = mock(HeroesRepository::class.java)
    @Mock
    lateinit var activityListener: ActivityStatesListener

    val hero = Heroes.Hero(1, "superman", "holasuperman", 1, null, null)
    val results = Arrays.asList(hero)
    val data = Heroes.Data(results)
    val dataResult = Heroes.DataResult(data)

    private val mainPresenter = MainPresenter()

    @Before
    fun initTest() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun testLoadInitialSuccess() = runBlocking(Dispatchers.Main) {
        `when`(heroesRepository.getHeroes(0)).thenReturn(dataResult)
        mainPresenter.getHeroesFromRepository(0)
        verify(activityListener).onHeroesReady(dataResult.data.results)
    }
}