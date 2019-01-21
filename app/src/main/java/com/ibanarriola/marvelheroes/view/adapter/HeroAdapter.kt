package com.ibanarriola.marvelheroes.view.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ibanarriola.marvelheroes.R
import com.ibanarriola.marvelheroes.glide.GlideApp
import com.ibanarriola.marvelheroes.repository.model.Heroes
import kotlinx.android.synthetic.main.hero_item.view.*
import java.text.DecimalFormat

class HeroAdapter(val heroes: List<Heroes.Hero>) : RecyclerView.Adapter<HeroViewHolder>() {


    override fun getItemCount() = heroes.size

    private lateinit var heroViewHolder: HeroViewHolder
    private lateinit var onHeroClickListener: OnHeroClickListener

    override fun onBindViewHolder(holder: HeroViewHolder, position: Int) {
        holder.bind(heroes[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeroViewHolder {
        heroViewHolder = HeroViewHolder.create(parent)
        heroViewHolder.setOnHeroClickListener(onHeroClickListener)
        return heroViewHolder
    }

    fun setHeroClickListener(onHeroClickListener: OnHeroClickListener) {
        this.onHeroClickListener = onHeroClickListener
    }
}

class HeroViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private lateinit var onHeroClickListener: OnHeroClickListener

    fun setOnHeroClickListener(onHeroClickListener: OnHeroClickListener) {
        this.onHeroClickListener = onHeroClickListener
    }

    fun bind(hero: Heroes.Hero?) {
        if (hero != null) {
            itemView.hero_name.text = hero.title
            val df = DecimalFormat("0.##")
            if (hero.prices!![0].price != 0.0) {
                itemView.hero_price.visibility = View.VISIBLE
                itemView.hero_price.text = itemView.context.getString(R.string.price_text, df.format(hero.prices[0].price))
            } else {
                itemView.hero_price.visibility = View.GONE
            }
            GlideApp.with(itemView)
                    .load(hero.thumbnail!!.path + "/standard_large." + hero.thumbnail.extension)
                    .placeholder(R.mipmap.marvel)
                    .into(itemView.hero_image)
            itemView.setOnClickListener {
                onHeroClickListener.onHeroClick(hero)
            }
        }
    }

    companion object {
        fun create(parent: ViewGroup): HeroViewHolder {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.hero_item, parent, false)
            return HeroViewHolder(view)
        }
    }
}

interface OnHeroClickListener {
    fun onHeroClick(hero: Heroes.Hero)
}