package com.ibanarriola.testapplication.view.activity

import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.LinearLayout
import com.ibanarriola.testapplication.R
import com.ibanarriola.testapplication.repository.datasource.State
import com.ibanarriola.testapplication.repository.model.Heroes
import com.ibanarriola.testapplication.view.adapter.HeroAdapter
import com.ibanarriola.testapplication.view.adapter.OnHeroClickListener
import com.ibanarriola.testapplication.view.presenter.MainPresenter
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity(), OnHeroClickListener {

    @Inject
    lateinit var app : Context
    @Inject
    lateinit var mainPresenter: MainPresenter

    private lateinit var heroesAdapter: HeroAdapter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initAdapter()
        initState()
    }

    override fun OnHeroClick(hero: Heroes.Hero) {
        val intent = Intent(this, HeroDetailActivity::class.java)
        intent.putExtra("hero", hero)
        startActivity(intent)
    }

    private fun initAdapter() {
        mainPresenter.findHeroes()
        heroesAdapter = HeroAdapter()
        heroes_list.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        heroes_list.adapter = heroesAdapter
        mainPresenter.heroesList.observe(this, Observer {
            heroesAdapter.submitList(it)
        })
        heroesAdapter.setHeroClickListener(this)
    }

    private fun initState() {
        mainPresenter.getState().observe(this, Observer { state ->
            progress.visibility = if (mainPresenter.listIsEmpty() && state == State.LOADING) View.VISIBLE else View.GONE
            if (!mainPresenter.listIsEmpty()) {
                heroesAdapter.setState(state ?: State.DONE)
            }
        })
    }

}
