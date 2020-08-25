package com.allens.largerglide

import android.annotation.SuppressLint
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import androidx.lifecycle.MutableLiveData
import com.allens.largerglide.impl.CustomRequestListener
import com.allens.largerglide.impl.ProgressListener
import com.allens.largerglide.interceptor.ProgressInterceptor
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.starot.larger.impl.OnCheckImageCacheListener
import com.starot.larger.impl.OnImageLoad
import com.starot.larger.impl.OnLoadProgressListener
import com.starot.larger.impl.OnLoadProgressPrepareListener
import java.io.File

//Glide 加载图片 这里记录了 加载的进度 和 加载状态 后续考虑 移植到其他地方 以便于拓展
class GlideImageLoader(private val context: Context) : OnImageLoad {


    private val progress = object : ProgressListener {
        override fun onProgress(progress: Int) {

            val value = progressViewLiveData?.value
            if (value == null || value) {
                progressViewLiveData?.postValue(false)
            }

            //进度
            progressLiveData?.postValue(progress)
        }
    }

    private val handler: Handler = Handler(Looper.getMainLooper())

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

    override fun checkCache(url: String, listener: OnCheckImageCacheListener) {
        Thread {
            val file: File? = try {
                Glide.with(context).downloadOnly()
                    .load(url)
                    .apply(
                        RequestOptions().onlyRetrieveFromCache(true)
                    ).submit()
                    .get()
            } catch (e: Exception) {
                null
            }
            if (file == null) {
                handler.post {
                    listener.onNoCache()
                }
            } else {
                handler.post {
                    listener.onHasCache()
                }
            }
        }.start()
    }

    override fun clearCache() {
        Glide.get(context).clearMemory()
        Thread { Glide.get(context).clearDiskCache() }.start()
    }

    override fun onPrepareLoadProgress(progressLiveData: MutableLiveData<Int>) {
        this.progressLiveData = progressLiveData
    }

    override fun onPrepareProgressView(progressViewLiveData: MutableLiveData<Boolean>) {
        this.progressViewLiveData = progressViewLiveData
    }


}