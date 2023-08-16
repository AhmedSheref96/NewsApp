package com.el3sas.newsapp.helpers

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.el3sas.newsapp.R

@BindingAdapter(
    "app:img_url"
)
fun bindImgWithGlide(imageView: ImageView, url: String? = null) {
    Glide.with(imageView).load(url).placeholder(R.drawable.placeholder).centerCrop().into(imageView)
}