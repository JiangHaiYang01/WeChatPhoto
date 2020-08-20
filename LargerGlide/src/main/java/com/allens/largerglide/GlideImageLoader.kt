package com.allens.largerglide

import android.annotation.SuppressLint
import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.starot.larger.impl.OnImageLoad
import android.graphics.drawable.Drawable
import com.allens.largerglide.impl.CustomRequestListener
import com.allens.largerglide.impl.ProgressListener
import com.allens.largerglide.interceptor.ProgressInterceptor
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target

class GlideImageLoader(private val context: Context) : OnImageLoad {

    private val progress = object : ProgressListener {
        override fun onProgress(progress: Int) {
        }
    }


    @SuppressLint("CheckResult")
    override fun load(url: String, isLoadFull: Boolean, imageView: ImageView) {
        ProgressInterceptor.addListener(url, progress)

        val options = RequestOptions()
        if (isLoadFull) {
            options
                .placeholder(imageView.drawable)
                .override(imageView.width, imageView.height)
        }
        Glide.with(context)
            .load(url)
            .apply(options)
            .listener(CustomRequestListener(url))
            .into(imageView)
    }

    override fun onLoadFailed(throwable: Throwable) {

    }

    override fun onLoadSuccess() {
    }


    override fun onLoadProgress(progress: Int) {

    }

}