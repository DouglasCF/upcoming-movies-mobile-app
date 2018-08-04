package com.arctouch.codechallenge.view

import android.databinding.BindingAdapter
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

object BindingAdapters {

    @JvmStatic
    @BindingAdapter("imageUrl", "placeholder", "centerCrop")
    fun ImageView.loadImage(url: String, placeholder: Drawable?, centerCrop: Boolean = false) {
        val options = RequestOptions().placeholder(placeholder)
        if (centerCrop) options.centerCrop()
        Glide.with(context).load(url).apply(options).into(this)
    }
}