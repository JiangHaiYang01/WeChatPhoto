package com.allens.largerglide

import android.annotation.SuppressLint
import android.content.Context
import android.widget.ImageView
import androidx.lifecycle.MutableLiveData
import com.allens.largerglide.impl.CustomRequestListener
import com.allens.largerglide.impl.ProgressListener
import com.allens.largerglide.interceptor.ProgressInterceptor
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.starot.larger.event.MyMutableLiveData
import com.starot.larger.impl.OnImageLoad

class GlideImageLoader(private val context: Context) : OnImageLoad {


    private val progress = object : ProgressListener {
        override fun onProgress(progress: Int) {
            //进度
            progressLiveData?.postValue(progress)


            val value = progressViewLiveData?.value
            if (value == null) {
                progressViewLiveData?.postValue(false)
            }
        }
    }

    private var progressLiveData: MutableLiveData<Int>? = null
    private var progressViewLiveData: MutableLiveData<Boolean>? = null


    @SuppressLint("CheckResult")
    override fun load(url: String, isLoadFull: Boolean, imageView: ImageView) {

        val options = RequestOptions()
        if (isLoadFull) {
            ProgressInterceptor.addListener(url, progress)
            options
                .placeholder(imageView.drawable)
                .override(imageView.width, imageView.height)
            //测试使用
//                .skipMemoryCache(true)
//                .diskCacheStrategy(DiskCacheStrategy.NONE)
        }
        Glide.with(context)
            .load(url)
            .apply(options)
            .listener(CustomRequestListener(url, progressViewLiveData, isLoadFull))
            .into(imageView)
    }

    override fun onPrepareLoadProgress(progressLiveData: MutableLiveData<Int>) {
        this.progressLiveData = progressLiveData
    }

    override fun onPrepareProgressView(progressViewLiveData: MutableLiveData<Boolean>) {
        this.progressViewLiveData = progressViewLiveData
    }


    override fun onLoadProgress(progress: Int) {

    }

    override fun onProgressChange(isGone: Boolean) {

    }

}