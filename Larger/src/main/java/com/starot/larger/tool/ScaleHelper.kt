package com.starot.larger.tool

import android.content.Context

object ScaleHelper {

    //图片比例
    fun getImgScale(width: Float, height: Float): Float {
        return width * 1.0f / height
    }

    //手机比例
    fun getWindowScale(context: Context): Float {
        return getWindowWidth(context.applicationContext) * 1.0f / getWindowHeight(context.applicationContext)
    }


    //手机屏幕 高度
    fun getWindowHeight(context: Context): Int {
        return context.applicationContext.resources.displayMetrics.heightPixels
    }

    //手机屏幕 宽度
    fun getWindowWidth(context: Context): Int {
        return context.applicationContext.resources.displayMetrics.widthPixels
    }
}