package com.starot.larger.anim.impl

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.starot.larger.Larger
import com.starot.larger.anim.AnimBgHelper
import com.starot.larger.anim.AnimEnterHelper
import com.starot.larger.utils.LogUtils

//动画的逻辑
interface AnimListener : OnAnimatorListener {


    /***
     *
     * 入场动画
     *
     * [parentView]                      根视图
     * [duration]                        间隔时间
     * [fullView]                        大图id
     * [thumbnailView]                   缩略图
     */
    fun enterAnimStart(
        parentView: View,
        duration: Long,
        fullView: View,
        thumbnailView: View?
    ) {
        LogUtils.i("入场动画 start")
        AnimEnterHelper.start(
            duration,
            fullView,
            thumbnailView,
            this,
        )
        //背景颜色变化
        AnimBgHelper.enter(parentView, 0f, duration)
    }
}