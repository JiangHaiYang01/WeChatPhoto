package com.starot.larger.impl

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.lifecycle.MutableLiveData

//加载图片接口
interface OnVideoLoadListener : OnLoadProgressPrepareListener, OnVideoViewIdListener {

    //设置预览图
    fun onAudioThumbnail(itemView: View, drawable: Drawable)

    //加载视屏
    fun load(url: String, view: View)

    //暂停
    fun pause()

    //销毁资源
    fun stop()
}


interface OnVideoViewIdListener {
    //返回视屏的id
    fun getVideoViewId(): Int

    //返回布局id
    fun getVideoLayoutId(): Int
}