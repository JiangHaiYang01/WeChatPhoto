package com.starot.larger.status

import androidx.lifecycle.MutableLiveData
import com.starot.larger.enums.AnimStatus

open class LargerStatus {

    companion object {
        var status = MutableLiveData<AnimStatus>().apply {
            postValue(AnimStatus.NOME)
        }
        //是否已经加载过了
        var isLoad = false
    }


}