package com.starot.larger.impl

import android.view.View
import android.widget.ImageView
import android.widget.VideoView

interface OnVideoLoadListener : OnLoadProgressPrepareListener {

    //加载视屏
    fun load(url: String, videoView: VideoView)

    //暂停
    fun pause()

    //销毁资源
    fun clear()


}