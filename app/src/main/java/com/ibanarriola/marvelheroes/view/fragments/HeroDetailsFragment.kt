package com.ibanarriola.marvelheroes.view.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.ibanarriola.marvelheroes.R
import com.ibanarriola.marvelheroes.glide.GlideApp
import com.ibanarriola.marvelheroes.repository.model.Heroes
import kotlinx.android.synthetic.main.activity_hero_detail.*
import java.text.DecimalFormat

class HeroDetailsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.activity_hero_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val hero: Heroes.Hero? = arguments!!.getParcelable("hero")
        if (hero != null) {
            hero_description.text = hero.description
            hero_name.text = hero.title
            val df = DecimalFormat("0.##")
            if (hero.prices!![0].price != 0.0)
                hero_price.text = getString(R.string.price_text, df.format(hero.prices!![0].price))
            else
                hero_price.visibility = View.GONE
            GlideApp.with(this)
                    .load(hero.thumbnail!!.path + "/standard_fantastic." + hero.thumbnail.extension)
                    .placeholder(R.mipmap.marvel)
                    .into(hero_image)
        }
    }
}