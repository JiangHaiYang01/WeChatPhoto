package com.starot.larger.impl

import android.view.View
import android.widget.ImageView
import android.widget.VideoView

interface OnVideoLoadListener : OnLoadProgressPrepareListener, OnVideoViewIdListener {

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

    //返回image的id
    fun getVideoFullId(): Int

    //返回布局id
    fun getVideoLayoutId():Int
}