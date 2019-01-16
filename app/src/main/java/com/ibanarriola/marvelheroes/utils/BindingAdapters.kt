package com.ibanarriola.marvelheroes.utils

import android.databinding.BindingAdapter
import android.widget.ImageView
import com.ibanarriola.marvelheroes.R
import com.ibanarriola.marvelheroes.glide.GlideApp

@BindingAdapter("bind:photoFilePath")
fun loadPhotoFilePath(imageView: ImageView, path: String) {
    GlideApp.with(imageView)
            .load(path)
            .placeholder(R.mipmap.marvel)
            .into(imageView)
}