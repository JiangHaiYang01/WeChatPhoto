package com.starot.larger.utils

import android.util.Log
import com.starot.larger.Larger

object LogUtils {


    private const val TAG = "allens_tag"

    @JvmStatic
    fun i(info: String) {
        if (Larger.largerConfig?.debug == true)
            Log.e(TAG, info)
    }
}