package com.ibanarriola.marvelheroes.view.fragments

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.navigation.findNavController
import com.github.salomonbrys.kodein.instance
import com.ibanarriola.marvelheroes.R
import com.ibanarriola.marvelheroes.kodein.heroesRepositoryModel
import com.ibanarriola.marvelheroes.repository.datasource.State
import com.ibanarriola.marvelheroes.repository.model.Heroes
import com.ibanarriola.marvelheroes.view.adapter.HeroAdapter
import com.ibanarriola.marvelheroes.view.adapter.OnHeroClickListener
import com.ibanarriola.marvelheroes.view.presenter.MainViewModel
import kotlinx.android.synthetic.main.fragment_heroes_list.*

class HeroesListFragment : Fragment(), OnHeroClickListener {

    lateinit var currentView: View

    override fun onHeroClick(hero: Heroes.MapHero) {
        val bundle = Bundle()
        bundle.putParcelable("hero", hero)
        currentView.findNavController().navigate(R.id.action_heroes_list_fragment_to_fragment_hero_details, bundle)
    }

    private lateinit var heroesAdapter: HeroAdapter
    private val mainViewModel: MainViewModel = heroesRepositoryModel.instance()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        currentView = inflater.inflate(R.layout.fragment_heroes_list, container, false)
        return currentView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initAdapter()
        initState()
    }

    private fun initAdapter() {
        heroesAdapter = HeroAdapter()
        heroes_list.layoutManager = LinearLayoutManager(activity, LinearLayout.VERTICAL, false)
        heroes_list.adapter = heroesAdapter
        mainViewModel.heroesList.observe(this, Observer {
            heroesAdapter.submitList(it)
        })
        heroesAdapter.setHeroClickListener(this)
    }

    private fun initState() {
        mainViewModel.getState().observe(this, Observer { state ->
            progress.visibility = if (mainViewModel.listIsEmpty() && state == State.LOADING) View.VISIBLE else View.GONE
            if (!mainViewModel.listIsEmpty()) {
                heroesAdapter.setState(state ?: State.DONE)
            }
        })
    }
}