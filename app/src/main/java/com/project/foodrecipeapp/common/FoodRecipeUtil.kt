package com.project.foodrecipeapp.common

import android.content.Context
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.project.foodrecipeapp.R

fun circularProgressDrawableImageLoad(context: Context): CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {
        strokeWidth = 8f
        centerRadius = 48f
        setTint(ContextCompat.getColor(context,R.color.grey))
        start()
    }
}

fun ImageView.loadImage(url: String, progressDrawable: CircularProgressDrawable){
    val options = RequestOptions()
        .placeholder(progressDrawable)
        .error(R.drawable.ic_baseline_error_24)
    Glide.with(context)
        .setDefaultRequestOptions(options)
        .load(url)
        .into(this)
}

@BindingAdapter("loadImage")
fun loadImage(imageView: ImageView, url: String?){
    if (url != null){
        imageView.loadImage(url, circularProgressDrawableImageLoad(imageView.context) )
    }
}