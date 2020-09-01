package com.starot.larger.utils

import android.util.Log

object LogUtils {


    private const val TAG = "allens_tag"

    fun i(info: String) {
        Log.e(TAG,info)
    }
}