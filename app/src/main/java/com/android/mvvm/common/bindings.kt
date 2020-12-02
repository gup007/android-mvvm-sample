package com.android.mvvm.common

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.mvvm.R
import com.squareup.picasso.Picasso

interface BindableAdapter<T> {
    fun setData(data: T?)
}

@BindingAdapter("data")
fun <T> setRecyclerViewProperties(recyclerView: RecyclerView, data: T) {
    if (recyclerView.adapter is BindableAdapter<*>) {
        (recyclerView.adapter as BindableAdapter<T>).setData(data)
    }
}

@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, imageUrl: String?) {
    if (imageUrl == null) {
        return
    }
    Picasso.get().load(imageUrl).placeholder(R.mipmap.ic_launcher).into(view)
}