package com.starot.larger.impl

import androidx.lifecycle.MutableLiveData
import com.starot.larger.event.MyMutableLiveData

interface OnLoadProgressPrepareListener {

    // 将记录是否显示加载的进度框 的 liveData 传入
    fun onPrepareProgressView(progressViewLiveData: MutableLiveData<Boolean>)

    // 将记录变化的 liveData 传入
    fun onPrepareLoadProgress(progressLiveData: MutableLiveData<Int>)



}