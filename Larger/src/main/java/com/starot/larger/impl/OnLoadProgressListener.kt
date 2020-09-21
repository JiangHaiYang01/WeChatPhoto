package com.starot.larger.impl

import android.content.Context
import android.view.View

interface OnLoadProgressListener : OnLifecycleListener {

    //进度条的变化
    fun onProgressChange(
        context: Context,
        view: View,
        progressId: Int,
        isFinish: Boolean,
        position: Int
    )

    //图片加载进度
    fun onLoadProgress(view: View, progressId: Int, progress: Int, position: Int)

}