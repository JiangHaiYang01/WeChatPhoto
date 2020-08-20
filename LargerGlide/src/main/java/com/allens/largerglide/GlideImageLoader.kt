package com.allens.largerglide

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.os.RecoverySystem
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.allens.largerglide.impl.CustomRequestListener
import com.allens.largerglide.impl.ProgressListener
import com.allens.largerglide.interceptor.ProgressInterceptor
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.starot.larger.impl.OnImageLoad

class GlideImageLoader(private val context: Context) : OnImageLoad {

    private val progress = object : ProgressListener {
        override fun onProgress(progress: Int) {
            onLoadProgress(progress)
        }
    }

    private var progressLiveData: MutableLiveData<Int>? = null


    @SuppressLint("CheckResult")
    override fun load(url: String, isLoadFull: Boolean, imageView: ImageView) {

        ProgressInterceptor.addListener(url, progress)
        val options = RequestOptions()
        if (isLoadFull) {
            options
                .placeholder(imageView.drawable)
                .override(imageView.width, imageView.height)
                //测试使用
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
        }
        Glide.with(context)
            .load(url)
            .apply(options)
            .listener(CustomRequestListener(url))
            .into(imageView)
    }

    override fun onPrepare(progressLiveData: MutableLiveData<Int>) {
        this.progressLiveData = progressLiveData
    }

    override fun onLoadProgress(progress: Int) {
        progressLiveData?.postValue(progress)
    }

}