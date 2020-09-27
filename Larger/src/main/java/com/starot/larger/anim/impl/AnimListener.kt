package com.starot.larger.anim.impl

import android.view.View
import com.starot.larger.anim.AnimBgHelper
import com.starot.larger.anim.AnimDragHelper
import com.starot.larger.anim.AnimEnterHelper
import com.starot.larger.anim.AnimExitHelper
import com.starot.larger.enums.AnimType
import com.starot.larger.utils.LogUtils

//动画的逻辑
interface AnimListener : OnAnimatorListener, OnDragAnimListener {


    fun enterAnimStart(
        parentView: View,
        duration: Long,
        fullView: View?,
        thumbnailView: View?
    ) {
        LogUtils.i("入场动画 start")
        if (fullView == null) {
            return
        }
        AnimEnterHelper.start(
            AnimType.ENTER,
            duration,
            fullView,
            thumbnailView,
            this,
        )
        //背景颜色变化
        AnimBgHelper.enter(parentView, 0f, duration)
    }

    fun dragResumeAnimStart(
        start: Float,
        parentView: View,
        duration: Long,
        fullView: View?,
        thumbnailView: View?
    ) {
        LogUtils.i("drag动画 start")
        if (fullView == null) {
            return
        }
        AnimDragHelper.start(
            AnimType.DRAG_RESUME,
            duration,
            fullView,
            thumbnailView,
            this,
        )
        //背景颜色变化
        AnimBgHelper.enter(parentView, start, duration)
    }


    fun exitAnimStart(
        parentView: View,
        duration: Long,
        fullView: View?,
        thumbnailView: View?,
    ) {
        exitAnimStart(AnimType.EXIT, 1.0f, parentView, duration, fullView, thumbnailView)
    }

    fun exitAnimStart(
        type: AnimType,
        start: Float,
        parentView: View,
        duration: Long,
        fullView: View?,
        thumbnailView: View?,
    ) {
        LogUtils.i("退场动画 start")
        if (fullView == null) {
            LogUtils.i("退场动画 start fullView == null")
            return
        }
        AnimExitHelper.start(
            type,
            duration,
            fullView,
            thumbnailView,
            this
        )
        //背景颜色变化
        AnimBgHelper.exit(parentView, start, duration)
    }

}