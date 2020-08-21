package com.starot.larger.impl

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.starot.larger.event.MyMutableLiveData

interface OnLoadProgressListener {

    //进度条的变化
    fun onProgressChange(context: Context, isGone: Boolean)

    //图片加载进度
    fun onLoadProgress(progress: Int)
}