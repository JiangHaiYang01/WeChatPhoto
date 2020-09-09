package com.starot.larger.impl

import android.content.Context

interface OnLoadProgressListener {

    //进度条的变化
    fun onProgressChange(context: Context, isFinish: Boolean)

    //图片加载进度
    fun onLoadProgress(progress: Int)
}