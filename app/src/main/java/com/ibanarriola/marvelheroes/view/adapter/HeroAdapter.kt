package com.ibanarriola.marvelheroes.view.adapter

import android.arch.paging.PagedListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ibanarriola.marvelheroes.databinding.HeroItemBinding
import com.ibanarriola.marvelheroes.repository.datasource.State
import com.ibanarriola.marvelheroes.repository.model.Heroes
import kotlinx.android.synthetic.main.hero_item.view.*

class HeroAdapter : PagedListAdapter<Heroes.MapHero, HeroViewHolder>(HeroDiffCallback) {

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
        val HeroDiffCallback = object : DiffUtil.ItemCallback<Heroes.MapHero>() {
            override fun areItemsTheSame(oldItem: Heroes.MapHero, newItem: Heroes.MapHero): Boolean {
                return oldItem.title == newItem.title
            }

            override fun areContentsTheSame(oldItem: Heroes.MapHero, newItem: Heroes.MapHero): Boolean {
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

class HeroViewHolder(val binding: HeroItemBinding) : RecyclerView.ViewHolder(binding.root) {

    private lateinit var onHeroClickListener: OnHeroClickListener

    fun setOnHeroClickListener(onHeroClickListener: OnHeroClickListener) {
        this.onHeroClickListener = onHeroClickListener
    }

    fun bind(hero: Heroes.MapHero?) {
        if (hero != null) {
            binding.hero = hero
            binding.executePendingBindings()
            if (hero.price.equals("0€")) {
                itemView.hero_price.visibility = View.GONE
            }
            itemView.setOnClickListener {
                onHeroClickListener.OnHeroClick(hero)
            }
        }
    }

    companion object {
        fun create(parent: ViewGroup): HeroViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val heroItemBinding: HeroItemBinding = HeroItemBinding.inflate(inflater, parent, false)
            return HeroViewHolder(heroItemBinding)
        }
    }
}

interface OnHeroClickListener {
    fun OnHeroClick(hero: Heroes.MapHero)
}