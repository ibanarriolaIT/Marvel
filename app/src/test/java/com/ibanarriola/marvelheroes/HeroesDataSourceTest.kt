package com.ibanarriola.marvelheroes

import android.arch.paging.DataSource
import android.arch.paging.PageKeyedDataSource
import com.ibanarriola.marvelheroes.repository.HeroesRepository
import com.ibanarriola.marvelheroes.repository.datasource.heroes.HeroesDataSource
import com.ibanarriola.marvelheroes.repository.model.Heroes
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Test
import io.reactivex.subjects.PublishSubject
import org.mockito.*
import org.mockito.Mockito.*
import java.util.*


class HeroesDataSourceTest {

    val heroesRepository: HeroesRepository = mock(HeroesRepository::class.java)
    @Mock
    lateinit var params: PageKeyedDataSource.LoadInitialParams<Int>
    @Mock
    lateinit var callback: PageKeyedDataSource.LoadInitialCallback<Int, Heroes.Hero>

    val hero = Heroes.Hero(1, "superman", "holasuperman", 1, null, null)
    val results = Arrays.asList(hero)
    val data = Heroes.Data(results)
    val dataResult = Heroes.DataResult(data)

    val compositeDisposable = CompositeDisposable()

    lateinit var heroesDataSource: HeroesDataSource
    private val heroesPublishSubject = PublishSubject.create<Heroes.DataResult>()

    @Before
    fun initTest(){
        MockitoAnnotations.initMocks(this)

    }

    @Test
    fun testLoadInitialSuccess(){
//        doReturn(heroesPublishSubject.singleOrError()).`when`(heroesRepository.getHeroes(ArgumentMatchers.anyInt()))
        `when`(heroesRepository.getHeroes(1)).thenReturn(heroesPublishSubject.singleOrError())
        heroesDataSource = HeroesDataSource(heroesRepository, compositeDisposable)
        val testObserver = TestObserver<Heroes.DataResult>()
        heroesDataSource.loadInitial(params, callback)
        heroesPublishSubject.onNext(dataResult)
        testObserver.assertComplete()
    }

}