package com.starot.larger.impl

import android.widget.ImageView
import androidx.lifecycle.MutableLiveData

//加载图片接口
interface OnImageLoadListener : OnLoadProgressPrepareListener {


    //加载图片
    fun load(url: String, isLoadFull: Boolean, imageView: ImageView)

    //判断是有有缓存
    fun checkCache(url: String, listener: OnImageCacheListener)

    //清理缓存
    fun clearCache()
}

//是否有缓存
interface OnImageCacheListener {
    fun onCache(hasCache: Boolean)
}

interface OnLoadProgressPrepareListener {

    // 将记录是否显示加载的进度框 的 liveData 传入
    fun onPrepareProgressView(progressViewLiveData: MutableLiveData<Boolean>)

    // 将记录变化的 liveData 传入
    fun onPrepareLoadProgress(progressLiveData: MutableLiveData<Int>)

}