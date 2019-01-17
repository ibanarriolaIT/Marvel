package com.ibanarriola.marvelheroes

import android.arch.paging.PageKeyedDataSource
import com.ibanarriola.marvelheroes.repository.HeroesRepository
import com.ibanarriola.marvelheroes.repository.datasource.heroes.HeroesDataSource
import com.ibanarriola.marvelheroes.repository.model.Heroes
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.TestObserver
import io.reactivex.schedulers.TestScheduler
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations
import java.util.*
import java.util.concurrent.TimeUnit


class HeroesDataSourceTest {

    val heroesRepository: HeroesRepository = mock(HeroesRepository::class.java)
    @Mock
    lateinit var params: PageKeyedDataSource.LoadInitialParams<Int>
    @Mock
    lateinit var callback: PageKeyedDataSource.LoadInitialCallback<Int, Heroes.Hero>

    private val testScheduler = TestScheduler()
    val hero = Heroes.Hero(1, "superman", "holasuperman", 1, null, null)
    val results = Arrays.asList(hero)
    val data = Heroes.Data(results)
    val dataResult = Heroes.DataResult(data)

    val compositeDisposable = CompositeDisposable()

    lateinit var heroesDataSource: HeroesDataSource

    @Before
    fun initTest() {
        MockitoAnnotations.initMocks(this)

    }

    @Test
    fun testLoadInitialSuccess() {
        val observable = Observable.just(dataResult).delay(1, TimeUnit.SECONDS, testScheduler)
        heroesDataSource = HeroesDataSource(heroesRepository, compositeDisposable)
        val testObserver = TestObserver<Heroes.DataResult>()
        observable.subscribe(testObserver)
        `when`(heroesRepository.getHeroes(0)).thenReturn(observable.singleOrError())
        heroesDataSource.loadInitial(params, callback)
        testScheduler.advanceTimeBy(950, TimeUnit.MILLISECONDS)
        testObserver.assertNotComplete()
        testScheduler.advanceTimeBy(950, TimeUnit.MILLISECONDS)
        testObserver.assertComplete()
    }

}