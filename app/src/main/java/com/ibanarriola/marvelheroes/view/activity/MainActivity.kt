package com.ibanarriola.marvelheroes.view.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import com.ibanarriola.marvelheroes.R
import com.ibanarriola.marvelheroes.repository.model.Heroes
import com.ibanarriola.marvelheroes.view.adapter.HeroAdapter
import com.ibanarriola.marvelheroes.view.adapter.OnHeroClickListener
import com.ibanarriola.marvelheroes.view.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), OnHeroClickListener {
    private lateinit var heroesAdapter: HeroAdapter
    private val heroesList = mutableListOf<Heroes.Hero>()
    private var page = 0
    private var progressBarUpdated = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initAdapter()
        findHeroes()
    }

    override fun onHeroClick(hero: Heroes.Hero) {
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
                    progress.visibility = View.VISIBLE
                    findHeroes()
                }

            }
        })
    }

    private fun getViewModel(page: Int) = ViewModelProviders.of(this)
            .get(MainViewModel::class.java)
            .getHeroesFromRepository(page)

    private fun findHeroes() {
        progress.visibility = View.VISIBLE
        getViewModel(page).observe(this, Observer<Heroes.DataResult> { heroes ->
            modifyProgressBar()
            progress.visibility = View.GONE
            if (heroes?.data?.results == null) {
                showError(getString(R.string.hero_error))
                return@Observer
            }
            heroesList.addAll(heroes.data.results)
            heroesAdapter.notifyDataSetChanged()
        })
    }

    fun showError(error: String?) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
    }

    private fun modifyProgressBar() {
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
