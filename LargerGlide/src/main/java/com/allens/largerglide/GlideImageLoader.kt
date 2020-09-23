package com.allens.largerglide

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import androidx.lifecycle.MutableLiveData
import com.allens.largerglide.impl.CustomRequestListener
import com.allens.largerglide.impl.ProgressListener
import com.allens.largerglide.interceptor.ProgressInterceptor
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.starot.larger.impl.OnImageCacheListener
import com.starot.larger.impl.OnImageLoadListener
import com.starot.larger.impl.OnImageLoadReadyListener
import com.starot.larger.utils.LogUtils
import java.io.File

//Glide 加载图片 这里记录了 加载的进度 和 加载状态 后续考虑 移植到其他地方 以便于拓展
class GlideImageLoader(private val context: Context) : OnImageLoadListener {


    private val handler: Handler = Handler(Looper.getMainLooper())

    private val statusMap = HashMap<Int, MutableLiveData<Boolean>>()
    private val progressMap = HashMap<Int, MutableLiveData<Int>>()


    @SuppressLint("CheckResult")
    override fun load(url: String, position: Int, isLoadFull: Boolean, imageView: ImageView) {

        val progressViewLiveData = statusMap[position]
        val progressLiveData = progressMap[position]
        val options = RequestOptions()
        if (isLoadFull) {
            ProgressInterceptor.addListener(url, object : ProgressListener {
                override fun onProgress(progress: Int) {

                    val value = progressViewLiveData?.value
                    if (value == null || value) {
                        progressViewLiveData?.value = (false)
                    }

                    //进度
                    progressLiveData?.value = (progress)
                }
            })
            options
                .placeholder(imageView.drawable)
                .override(imageView.width, imageView.height)
        }
        LogUtils.i("load image isLoadFull:$isLoadFull, url:$url")
        Glide.with(context)
            .load(url)
            .apply(options)
            .listener(CustomRequestListener(url, progressViewLiveData, isLoadFull))
            .into(imageView)
    }

    override fun load(
        url: String,
        position: Int,
        imageView: ImageView,
        listener: OnImageLoadReadyListener
    ) {
        Glide.with(context)
            .load(url)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    listener.onReady()
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    listener.onReady()
                    return false
                }
            })
            .into(imageView)

    }

    override fun checkCache(url: String, listener: OnImageCacheListener) {
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
                    listener.onCache(false)
                }
            } else {
                handler.post {
                    listener.onCache(true)
                }
            }
        }.start()
    }


    override fun clearCache() {
        Glide.get(context).clearMemory()
        Thread { Glide.get(context).clearDiskCache() }.start()
    }

    override fun onPrepareLoadProgress(progressLiveData: MutableLiveData<Int>, position: Int) {
        progressMap[position] = progressLiveData
    }

    override fun onPrepareProgressView(
        status: MutableLiveData<Boolean>,
        position: Int
    ) {
        statusMap[position] = status
    }


    override fun onDestroy() {
        super.onDestroy()
        statusMap.clear()
        progressMap.clear()
    }

}