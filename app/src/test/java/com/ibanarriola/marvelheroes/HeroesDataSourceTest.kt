package com.ibanarriola.marvelheroes

import android.arch.lifecycle.MutableLiveData
import com.ibanarriola.marvelheroes.repository.HeroesRepository
import com.ibanarriola.marvelheroes.repository.model.Heroes
import com.ibanarriola.marvelheroes.view.viewmodel.MainViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import java.util.*
import android.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.rules.TestRule


class HeroesDataSourceTest {

    @get:Rule
    var instantExecutorRule: TestRule = InstantTaskExecutorRule()
    @Mock
    lateinit var heroesRepository: HeroesRepository

    @Mock
    lateinit var deferred: Deferred<Heroes.DataResult>
    val hero = Heroes.Hero(1, "superman", "holasuperman", 1, null, null)
    val results = Arrays.asList(hero)
    val data = Heroes.Data(results)
    val dataResult = Heroes.DataResult(data)

    @Before
    fun initTest() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun testLoadInitialSuccess(): Unit = runBlocking {
        `when`(heroesRepository.getHeroes(0)).thenReturn(deferred)
        `when`(deferred.await()).thenReturn(dataResult)
        val liveData: MutableLiveData<List<Heroes.Hero>>
        val mainViewModel = MainViewModel(Dispatchers.Unconfined)
        liveData = mainViewModel.data
        mainViewModel.getHeroesFromRepository(0)
        delay(10000L)
        Assert.assertEquals(dataResult, liveData.value)
    }

}