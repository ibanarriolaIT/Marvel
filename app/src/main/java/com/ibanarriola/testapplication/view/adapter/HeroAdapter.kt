package com.ibanarriola.testapplication.view.adapter

import android.arch.paging.PagedListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ibanarriola.testapplication.R
import com.ibanarriola.testapplication.glide.GlideApp
import com.ibanarriola.testapplication.repository.datasource.State
import com.ibanarriola.testapplication.repository.model.Heroes
import kotlinx.android.synthetic.main.hero_item.view.*
import java.text.DecimalFormat

class HeroAdapter : PagedListAdapter<Heroes.Hero, HeroViewHolder>(HeroDiffCallback) {

    private var state = State.LOADING
    private lateinit var heroViewHolder: HeroViewHolder
    private lateinit var onHeroClickListener: OnHeroClickListener

    override fun onBindViewHolder(holder: HeroViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeroViewHolder {
        heroViewHolder = HeroViewHolder.create(parent)
        heroViewHolder.setOnHeroClickListener(onHeroClickListener)
        return heroViewHolder
    }

    companion object {
        val HeroDiffCallback = object : DiffUtil.ItemCallback<Heroes.Hero>() {
            override fun areItemsTheSame(oldItem: Heroes.Hero, newItem: Heroes.Hero): Boolean {
                return oldItem.title == newItem.title
            }

            override fun areContentsTheSame(oldItem: Heroes.Hero, newItem: Heroes.Hero): Boolean {
                return oldItem == newItem
            }
        }
    }

    fun setHeroClickListener(onHeroClickListener: OnHeroClickListener) {
        this.onHeroClickListener = onHeroClickListener
    }

    fun setState(state: State) {
        this.state = state
        notifyItemChanged(super.getItemCount())
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
                onHeroClickListener.OnHeroClick(hero)
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
    fun OnHeroClick(hero: Heroes.Hero)
}