package com.allens.largerglide.impl

import android.graphics.drawable.Drawable
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.allens.largerglide.interceptor.ProgressInterceptor
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

class CustomRequestListener(
    private val url: String,
    private val progressViewLiveData: MutableLiveData<Boolean>?,
    private val loadFull: Boolean
) :
    RequestListener<Drawable> {
    override fun onLoadFailed(
        e: GlideException?,
        model: Any?,
        target: Target<Drawable>?,
        isFirstResource: Boolean
    ): Boolean {
        ProgressInterceptor.removeListener(url = url)

        if (loadFull) {
            progressViewLiveData?.postValue(true)
        }
        return false
    }

    override fun onResourceReady(
        resource: Drawable?,
        model: Any?,
        target: Target<Drawable>?,
        dataSource: DataSource?,
        isFirstResource: Boolean
    ): Boolean {
        ProgressInterceptor.removeListener(url = url)

        if (loadFull) {
            progressViewLiveData?.postValue(true)
        }
        return false
    }


}