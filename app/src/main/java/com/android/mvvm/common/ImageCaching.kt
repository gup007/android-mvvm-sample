package com.android.mvvm.common

import android.content.Context
import android.net.Uri
import android.text.TextUtils
import android.widget.ImageView
import androidx.annotation.NonNull
import com.squareup.picasso.Picasso
import com.squareup.picasso.RequestCreator
import com.squareup.picasso.Transformation
import java.io.File
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ImageCaching @Inject constructor(private var context: Context) {

    var builder: RequestCreator? = null

    fun into(target: ImageView): ImageCaching {
        if (builder != null) {
            builder?.into(target)
        }
        return this
    }

    fun resize(targetWidth: Int, targetHeight: Int): ImageCaching {
        if (builder != null) {
            builder?.resize(targetWidth, targetHeight)
        }
        return this
    }

    fun centerCrop(): ImageCaching {
        if (builder != null) {
            builder?.centerCrop()
        }
        return this
    }

    fun fit(): ImageCaching {
        if (builder != null) {
            builder?.fit()
        }
        return this
    }


    fun centerInside(): ImageCaching {
        if (builder != null) {
            builder?.centerInside()
        }
        return this
    }

    fun placeholder(placeholderResId: Int): ImageCaching {
        if (builder != null) {
            builder?.placeholder(placeholderResId)
        }
        return this
    }

    fun fetch(callback: Callback?) {
        if (builder != null) {
            builder?.fetch(object : com.squareup.picasso.Callback {

                override fun onSuccess() {
                    if (callback != null) {
                        callback.onSuccess()
                        callback.onComplete()
                    }
                }

                override fun onError(e: Exception?) {
                    if (callback != null) {
                        callback.onError()
                        callback.onComplete()
                    }
                }
            })
        }
    }

    fun transformation(transform: Transformation): ImageCaching {
        if (builder != null) {
            builder!!.transform(transform)
        }
        return this
    }

    class SimpleCallback : Callback {

        override fun onSuccess() {

        }

        override fun onError() {

        }

        override fun onComplete() {

        }
    }

    interface Callback {
        fun onSuccess()

        fun onError()

        fun onComplete()
    }


    fun initialize(context: Context) {
        this.context = context
    }

    fun load(imageUrl: String): ImageCaching {
        val imageCaching = ImageCaching(context)
        if (TextUtils.isEmpty(imageUrl)) {
            return imageCaching
        }
        imageCaching.builder = Picasso.get().load(imageUrl)
        return imageCaching
    }

    fun load(uri: Uri): ImageCaching {
        val imageCaching = ImageCaching(context)
        imageCaching.builder = Picasso.get().load(uri)
        return imageCaching
    }

    fun load(imageUrl: String, @NonNull transformation: Transformation): ImageCaching {
        val imageCaching = ImageCaching(context)
        if (TextUtils.isEmpty(imageUrl)) {
            return imageCaching
        }
        imageCaching.builder = Picasso.get().load(imageUrl).transform(transformation)
        return imageCaching
    }

    fun load(file: File?): ImageCaching {
        val imageCaching = ImageCaching(context)
        if (file == null) {
            return imageCaching
        }
        imageCaching.builder = Picasso.get().load(file)
        return imageCaching
    }
}
