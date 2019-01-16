package com.ibanarriola.marvelheroes.repository.datasource.heroes

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.PageKeyedDataSource
import com.ibanarriola.marvelheroes.repository.HeroesRepository
import com.ibanarriola.marvelheroes.repository.datasource.State
import com.ibanarriola.marvelheroes.repository.model.Heroes
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Action
import io.reactivex.schedulers.Schedulers

class HeroesDataSource(private val heroesRepository: HeroesRepository,
                       private val compositeDisposable: CompositeDisposable)
    : PageKeyedDataSource<Int, Heroes.MapHero>() {

    var state: MutableLiveData<State> = MutableLiveData()
    private var retryCompletable: Completable? = null

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Heroes.MapHero>) {
        updateState(State.LOADING)
        compositeDisposable.add(heroesRepository.getHeroes(0).subscribe(
                { heroes ->
                    updateState(State.DONE)
                    val mapHeroes = mutableListOf<Heroes.MapHero>()
                    heroes.data.results.forEach { hero ->
                        mapHeroes.add(
                                Heroes.MapHero.ModelMapper.from(hero))
                    }
                    callback.onResult(mapHeroes,
                            null,
                            1
                    )
                },
                {
                    updateState(State.ERROR)
                    setRetry(Action { loadInitial(params, callback) })
                }
        ))
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Heroes.MapHero>) {
        updateState(State.LOADING)
        compositeDisposable.add(heroesRepository.getHeroes(params.key).subscribe(
                { heroes ->
                    updateState(State.DONE)
                    val mapHeroes = mutableListOf<Heroes.MapHero>()
                    heroes.data.results.forEach { hero ->
                        mapHeroes.add(
                                Heroes.MapHero.ModelMapper.from(hero))
                    }
                    callback.onResult(mapHeroes,
                            params.key + 1
                    )
                },
                {
                    updateState(State.ERROR)
                    setRetry(Action { loadAfter(params, callback) })
                }
        ))
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Heroes.MapHero>) {
    }

    private fun updateState(state: State) {
        this.state.postValue(state)
    }

    fun retry() {
        if (retryCompletable != null) {
            compositeDisposable.add(retryCompletable!!
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe())
        }
    }

    private fun setRetry(action: Action?) {
        retryCompletable = if (action == null) null else Completable.fromAction(action)
    }

}