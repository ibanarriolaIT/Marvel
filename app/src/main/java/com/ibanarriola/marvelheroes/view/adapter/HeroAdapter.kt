package com.ibanarriola.marvelheroes.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.ibanarriola.marvelheroes.databinding.HeroItemBinding
import com.ibanarriola.marvelheroes.repository.model.Heroes
import kotlinx.android.synthetic.main.hero_item.view.*

class HeroAdapter(val heroes: List<Heroes.MapHero>) : RecyclerView.Adapter<HeroViewHolder>() {


    override fun getItemCount() = heroes.size

    private lateinit var heroViewHolder: HeroViewHolder
    private lateinit var data: MutableLiveData<Heroes.MapHero>

    override fun onBindViewHolder(holder: HeroViewHolder, position: Int) {
        holder.bind(heroes[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeroViewHolder {
        heroViewHolder = HeroViewHolder.create(parent)
        heroViewHolder.setLiveData(data)
        return heroViewHolder
    }

    fun setLiveData(data: MutableLiveData<Heroes.MapHero>) {
        this.data = data
    }
}

class HeroViewHolder(val binding: HeroItemBinding) : RecyclerView.ViewHolder(binding.root) {

    private lateinit var data: MutableLiveData<Heroes.MapHero>

    fun setLiveData(data: MutableLiveData<Heroes.MapHero>) {
        this.data = data
    }

    fun onHeroClick(hero: Heroes.MapHero) {
        data.value = hero
    }

    fun bind(hero: Heroes.MapHero?) {
        if (hero != null) {
            binding.viewHolder = this
            binding.hero = hero
            binding.executePendingBindings()
            if (hero.price == "0€")
                itemView.hero_price.visibility = View.GONE
        }
    }

    companion object {
        fun create(parent: ViewGroup): HeroViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = HeroItemBinding.inflate(inflater)
            return HeroViewHolder(binding)
        }
    }
}
