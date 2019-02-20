package com.ibanarriola.marvelheroes

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.ibanarriola.marvelheroes.data.repository.HeroesRepository
import com.ibanarriola.marvelheroes.data.model.Heroes
import com.ibanarriola.marvelheroes.view.viewmodel.MainViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.stubbing.OngoingStubbing
import java.util.*


class HeroesDataSourceTest {


    inline fun <reified T> mock(): T =
            Mockito.mock(T::class.java)

    // To avoid having to use backticks for "when"
    fun <T> whenever(methodCall: T): OngoingStubbing<T> =
            Mockito.`when`(methodCall)

    @get:Rule
    var instantExecutorRule: TestRule = InstantTaskExecutorRule()
    val heroesRepository: HeroesRepository = mock()

    val deferred: Deferred<Heroes.DataResult> = mock()
    val price = Heroes.Prices(2.0)
    val prices = Arrays.asList(price)
    val thumbnail = Heroes.Thumbnail("image", "jpg")
    val hero = Heroes.Hero(1, "superman", "holasuperman", 1, thumbnail, prices)
    val results = Arrays.asList(hero)
    val data = Heroes.Data(results)
    val dataResult = Heroes.DataResult(data)
    val expectedResult = Heroes.MapHero("superman", "holasuperman", "2€", "image/standard_large.jpg", "image/standard_fantastic.jpg")

    @Before
    fun initTest() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun testLoadInitialSuccess(): Unit = runBlocking {
        whenever(heroesRepository.getHeroes(0)).thenReturn(deferred)
        whenever(deferred.await()).thenReturn(dataResult)
        val liveData: MutableLiveData<List<Heroes.MapHero>>
        val mainViewModel = MainViewModel(heroesRepository, Dispatchers.Unconfined)
        liveData = mainViewModel.data
        mainViewModel.getHeroesFromRepository(0)
        Assert.assertEquals(expectedResult, liveData.value?.get(0))
    }

}