package com.ibanarriola.marvelheroes.view.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ibanarriola.marvelheroes.R
import com.ibanarriola.marvelheroes.data.model.Heroes
import com.ibanarriola.marvelheroes.utils.observe
import com.ibanarriola.marvelheroes.view.adapter.HeroAdapter
import com.ibanarriola.marvelheroes.view.viewmodel.MainViewModel
import com.ibanarriola.marvelheroes.view.viewmodel.MainViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance


class MainActivity : AppCompatActivity(), KodeinAware {
    override val kodein by closestKodein()
    val viewModelFactory by instance<MainViewModelFactory>()
    private lateinit var heroesAdapter: HeroAdapter
    private lateinit var viewModel: MainViewModel
    private val heroesList = mutableListOf<Heroes.MapHero>()
    private var page = 0
    private var progressBarUpdated = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(MainViewModel::class.java)
        initAdapter()
        initObserver()
        findHeroes()
    }

    private fun initAdapter() {
        heroesAdapter = HeroAdapter(heroesList)
        heroes_list.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        heroes_list.adapter = heroesAdapter
        val adapterLiveData = MutableLiveData<Heroes.MapHero>()
        heroesAdapter.setLiveData(adapterLiveData)
        adapterLiveData.observe(this, Observer { hero ->
            val intent = Intent(this, HeroDetailActivity::class.java)
            intent.putExtra("hero", hero)
            startActivity(intent)
        })
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
        observe(viewModel.data) { heroes ->
            modifyProgressBar()
            progress.visibility = View.GONE
            if (heroes == null) {
                showError(getString(R.string.hero_error))
                return@observe
            }
            heroesList.addAll(heroes)
            heroesAdapter.notifyDataSetChanged()
        }
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
