package com.ibanarriola.testapplication.view.activity

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;
import android.view.View
import com.ibanarriola.testapplication.R
import com.ibanarriola.testapplication.glide.GlideApp
import com.ibanarriola.testapplication.repository.model.Heroes

import kotlinx.android.synthetic.main.activity_hero_detail.*

class HeroDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hero_detail)
        val intent = intent
        val hero = intent.getParcelableExtra<Heroes.Hero>("hero")
        hero_description.text = hero.description
        hero_name.text = hero.title
        if (hero.prices[0].price == 0.0)
            hero_price.text = hero.prices[0].price.toString() + "€"
        else
            hero_price.visibility = View.GONE
        GlideApp.with(this).load(hero.thumbnail.path + "/standard_fantastic." + hero.thumbnail.extension)
                .into(hero_image)
    }

}
