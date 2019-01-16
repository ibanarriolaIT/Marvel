package com.ibanarriola.marvelheroes.view.fragments

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ibanarriola.marvelheroes.R
import com.ibanarriola.marvelheroes.databinding.FragmentHeroDetailBinding
import com.ibanarriola.marvelheroes.repository.model.Heroes
import kotlinx.android.synthetic.main.fragment_hero_detail.view.*

class HeroDetailsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: FragmentHeroDetailBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_hero_detail, container, false)
        val myView: View = binding.root
        val hero: Heroes.MapHero? = arguments!!.getParcelable("hero")
        binding.hero = hero
        if(hero?.price.equals("0€"))
            myView.hero_price.visibility = View.GONE
        return myView
    }


}