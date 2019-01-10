package com.ibanarriola.marvelheroes

import android.arch.paging.DataSource
import android.arch.paging.PageKeyedDataSource
import com.ibanarriola.marvelheroes.repository.HeroesRepository
import com.ibanarriola.marvelheroes.repository.datasource.ApiDataSource
import com.ibanarriola.marvelheroes.repository.datasource.DataModule
import com.ibanarriola.marvelheroes.repository.model.Heroes
import com.ibanarriola.marvelheroes.view.presenter.MainPresenter
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Test
import io.reactivex.subjects.PublishSubject
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Rule
import org.mockito.*
import org.mockito.Mockito.*
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import java.util.*
import retrofit2.mock.Calls


class HeroesDataSourceTest {

    val heroesRepository: HeroesRepository = mock(HeroesRepository::class.java)
    @Mock
    lateinit var deferred: Deferred<Response<Heroes.DataResult>>
    @Mock
    lateinit var response: Response<Heroes.DataResult>

    val hero = Heroes.Hero(1, "superman", "holasuperman", 1, null, null)
    val results = Arrays.asList(hero)
    val data = Heroes.Data(results)
    val dataResult = Heroes.DataResult(data)

    private val mainPresenter = MainPresenter()

    @Before
    fun initTest(){
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun testLoadInitialSuccess() = runBlocking {
        `when`(deferred.await()).thenReturn(response)
        `when`(response.body()).thenReturn(dataResult)
        `when`(response.isSuccessful).thenReturn(true)
        `when`(heroesRepository.getHeroes(0)).thenReturn(deferred)
        mainPresenter.getHeroesFromRepository(0)
        val testObserver = TestObserver<Deferred<Response<Heroes.DataResult>>>()
        testObserver.assertComplete()
    }

}