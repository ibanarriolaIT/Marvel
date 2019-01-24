package com.ibanarriola.marvelheroes.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.ibanarriola.marvelheroes.R
import com.ibanarriola.marvelheroes.glide.GlideApp

@BindingAdapter("bind:photoFilePath")
fun loadPhotoFilePath(itemView: ImageView, url: String) {
    GlideApp.with(itemView)
            .load(url)
            .placeholder(R.mipmap.marvel)
            .into(itemView)
}