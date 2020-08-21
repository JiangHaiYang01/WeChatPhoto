package com.starot.larger.impl

import android.content.Context
import android.content.Intent

//启动act
interface OnBuilderStart {

    //启动默认的act
    fun start(context: Context)
    //启动自定义的act
    fun start(context: Context, cls: Class<*>)

}