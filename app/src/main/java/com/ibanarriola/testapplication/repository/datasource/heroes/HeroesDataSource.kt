package com.ibanarriola.testapplication.repository.datasource.heroes

import android.arch.paging.PageKeyedDataSource
import com.ibanarriola.testapplication.repository.HeroesRepository
import com.ibanarriola.testapplication.repository.model.Heroes
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Action
import io.reactivex.schedulers.Schedulers

class HeroesDataSource(private val heroesRepository: HeroesRepository,
                       private val compositeDisposable: CompositeDisposable)
    : PageKeyedDataSource<Int, Heroes.Hero>() {

    private var retryCompletable: Completable? = null

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Heroes.Hero>) {
        compositeDisposable.add(heroesRepository.getHeroes(0).subscribe(
                { heroes ->
                    callback.onResult(heroes.data.results,
                            null,
                            1
                    )
                },
                {
                    setRetry(Action { loadInitial(params, callback) })
                }
        ))
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Heroes.Hero>) {
        compositeDisposable.add(heroesRepository.getHeroes(params.key).subscribe(
                { heroes ->
                    callback.onResult(heroes.data.results,
                            params.key + 1
                    )
                },
                {
                    setRetry(Action { loadAfter(params, callback) })
                }
        ))
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Heroes.Hero>) {
    }

    private fun setRetry(action: Action?) {
        retryCompletable = if (action == null) null else Completable.fromAction(action)
    }

}