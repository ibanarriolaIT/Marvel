package com.ibanarriola.marvelheroes.view.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.ibanarriola.marvelheroes.R
import com.ibanarriola.marvelheroes.databinding.ActivityHeroDetailBinding
import com.ibanarriola.marvelheroes.repository.model.Heroes
import kotlinx.android.synthetic.main.activity_hero_detail.*


class HeroDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hero_detail)
        val intent = intent
        val hero = intent.getParcelableExtra<Heroes.MapHero>("hero")
        DataBindingUtil.setContentView<ActivityHeroDetailBinding>(this, R.layout.activity_hero_detail)
                .apply {
                    this.hero = hero
                }
        if (hero.price == "0€")
            hero_price.visibility = View.GONE
    }

}
