package com.ibanarriola.marvelheroes.view.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.Toast
import com.github.salomonbrys.kodein.instance
import com.ibanarriola.marvelheroes.R
import com.ibanarriola.marvelheroes.kodein.heroesRepositoryModel
import com.ibanarriola.marvelheroes.repository.model.Heroes
import com.ibanarriola.marvelheroes.view.adapter.HeroAdapter
import com.ibanarriola.marvelheroes.view.adapter.OnHeroClickListener
import com.ibanarriola.marvelheroes.view.presenter.MainPresenter
import kotlinx.android.synthetic.main.activity_main.*
import android.widget.RelativeLayout



class MainActivity : AppCompatActivity(), OnHeroClickListener, ActivityStates {
    private lateinit var heroesAdapter: HeroAdapter
    private val mainPresenter: MainPresenter = heroesRepositoryModel.instance()
    private val heroesList = mutableListOf<Heroes.Hero>()
    private var page = 0
    private var progressBarUpdated = false

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
        heroesAdapter = HeroAdapter(heroesList)
        heroes_list.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        heroes_list.adapter = heroesAdapter
        heroesAdapter.setHeroClickListener(this)
        heroes_list.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (heroesList.size - 8 < (recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                && progress.visibility == View.GONE) {
                    page++
                    mainPresenter.getHeroesFromRepository(page)
                }

            }
        })
    }

    private fun initState() {
        mainPresenter.setActivityListener(this)
        mainPresenter.getHeroesFromRepository(page)
    }

    override fun loading() {
        progress.visibility = View.VISIBLE
    }

    override fun onHeroesReady(heroes: List<Heroes.Hero>) {
        modifyProgressBar()
        progress.visibility = View.GONE
        heroesList.addAll(heroes)
        heroesAdapter.notifyDataSetChanged()
    }

    override fun onError(error: String?) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
    }

    private fun modifyProgressBar(){
        if (!progressBarUpdated) {
            progress.setBackgroundColor(getColor(R.color.grey50))
            val parameter = progress.layoutParams as LinearLayout.LayoutParams
            parameter.height = 100
            parameter.setMargins(0, -100, 0, 0)
            progress.layoutParams = parameter
            progressBarUpdated = true
        }
    }

}

interface ActivityStates {
    fun loading()
    fun onHeroesReady(hero: List<Heroes.Hero>)
    fun onError(error: String?)
}
