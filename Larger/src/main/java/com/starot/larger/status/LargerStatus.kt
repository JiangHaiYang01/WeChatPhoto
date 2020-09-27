package com.starot.larger.status

import androidx.lifecycle.MutableLiveData
import com.starot.larger.enums.AnimStatus
import com.starot.larger.enums.BackEnum
import com.starot.larger.impl.OnLifecycleListener
import com.starot.larger.livedata.UnViscousLiveData

object LargerStatus : OnLifecycleListener {

    var status = MutableLiveData<AnimStatus>().apply {
        value = (AnimStatus.NOME)
    }

    //是否已经加载过了
    var isLoad = MutableLiveData<Boolean>().apply {
        value = (false)
    }

    var pos = MutableLiveData<Int>()

    var back = MutableLiveData<BackEnum>().apply {
        value = BackEnum.BACK_NOME
    }

    override fun onDestroy() {
        super.onDestroy()
        isLoad.value = (false)
        back.value = BackEnum.BACK_NOME
        status.value = (AnimStatus.NOME)
    }

}