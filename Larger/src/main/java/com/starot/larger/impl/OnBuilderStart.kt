package com.starot.larger.impl

import android.content.Context
import android.content.Intent

interface OnBuilderStart {

    fun start(context: Context)
    fun start(context: Context, cls: Class<*>)

}