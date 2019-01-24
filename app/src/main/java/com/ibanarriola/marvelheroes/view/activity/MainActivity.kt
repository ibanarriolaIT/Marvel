package com.ibanarriola.marvelheroes.view.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ibanarriola.marvelheroes.R
import com.ibanarriola.marvelheroes.repository.model.Heroes
import com.ibanarriola.marvelheroes.view.adapter.HeroAdapter
import com.ibanarriola.marvelheroes.view.adapter.OnHeroClickListener
import com.ibanarriola.marvelheroes.view.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), OnHeroClickListener {
    private lateinit var heroesAdapter: HeroAdapter
    private lateinit var viewModel: MainViewModel
    private val heroesList = mutableListOf<Heroes.MapHero>()
    private var page = 0
    private var progressBarUpdated = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProviders.of(this)
                .get(MainViewModel::class.java)
        initAdapter()
        initObserver()
        findHeroes()
    }

    override fun onHeroClick(hero: Heroes.MapHero) {
        val intent = Intent(this, HeroDetailActivity::class.java)
        intent.putExtra("hero", hero)
        startActivity(intent)
    }

    private fun initAdapter() {
        heroesAdapter = HeroAdapter(heroesList)
        heroes_list.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        heroes_list.adapter = heroesAdapter
        heroesAdapter.setHeroClickListener(this)
        heroes_list.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (heroesList.size - 15 < (recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                        && progress.visibility == View.GONE) {
                    page++
                    progress.visibility = View.VISIBLE
                    findHeroes()
                }

            }
        })
    }

    private fun initObserver() {
        viewModel.data.observe(this, Observer { heroes ->
            modifyProgressBar()
            progress.visibility = View.GONE
            if (heroes == null) {
                showError(getString(R.string.hero_error))
                return@Observer
            }
            heroesList.addAll(heroes)
            heroesAdapter.notifyDataSetChanged()
        })
    }

    private fun findHeroes() {
        progress.visibility = View.VISIBLE
        viewModel.getHeroesFromRepository(page)
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
