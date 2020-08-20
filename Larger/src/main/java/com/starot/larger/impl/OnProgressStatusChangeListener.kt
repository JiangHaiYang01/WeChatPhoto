package com.starot.larger.impl

import androidx.lifecycle.MutableLiveData
import com.starot.larger.event.MyMutableLiveData

interface OnProgressStatusChangeListener {

    fun onPrepareProgressView(progressViewLiveData: MutableLiveData<Boolean>){}

    //进度条的变化
    fun onProgressChange(isGone: Boolean)


}