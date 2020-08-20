package com.starot.larger.impl

import androidx.lifecycle.MutableLiveData
import com.starot.larger.event.MyMutableLiveData

interface OnLoadProgress {


    // 将记录变化的 liveData 传入
    fun onPrepareLoadProgress(progressLiveData: MutableLiveData<Int>){}

    //图片加载进度
    fun onLoadProgress(progress: Int)
}