package com.starot.larger.impl

import android.view.View
import android.widget.ImageView
import androidx.lifecycle.MutableLiveData
import com.starot.larger.bean.LargerBean
import com.starot.larger.enums.LoadImageStatus

//加载图片接口
interface OnImageLoadListener : OnLoadProgressPrepareListener, OnLifecycleListener {


    //加载图片
    fun load(url: String, position: Int, isLoadFull: Boolean, imageView: ImageView)

    fun load(url: String, position: Int, imageView: ImageView, listener: OnImageLoadReadyListener?)

    //判断是有有缓存
    fun checkCache(url: String, listener: OnImageCacheListener)

    //清理缓存
    fun clearCache()
}

//是否有缓存
interface OnImageCacheListener {
    fun onCache(hasCache: Boolean)
}


interface OnImageLoadReadyListener {
    fun onLoadFailed();
    fun onReady()
}

//自行处理view
interface OnCustomImageLoadListener : OnLoadProgressListener {


    //动画开始以前做啥事情
    fun onDoBefore(
        progressLoadChangeLiveData:MutableLiveData<LoadImageStatus>,
        imageLoader: OnImageLoadListener?,
        view: View,
        position: Int,
        data: LargerBean
    )

    //动画结束以后做啥事情
    fun onDoAfter(
        progressLoadChangeLiveData:MutableLiveData<LoadImageStatus>,
        imageLoader: OnImageLoadListener?,
        view: View,
        position: Int,
        data: LargerBean
    )

}


interface OnLoadProgressPrepareListener {

    // 将记录是否显示加载的进度框 的 liveData 传入
    fun onPrepareProgressView(status: MutableLiveData<LoadImageStatus>, position: Int) {}

    // 将记录变化的 liveData 传入
    fun onPrepareLoadProgress(progressLiveData: MutableLiveData<Int>, position: Int) {}

}