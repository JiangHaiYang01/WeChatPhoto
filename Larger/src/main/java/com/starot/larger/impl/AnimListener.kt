package com.starot.larger.impl

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.starot.larger.anim.AnimBgHelper
import com.starot.larger.anim.AnimEnterHelper

//动画的逻辑
interface AnimListener {

    /***
     *
     * 小图到大图的动画逻辑
     *
     * [parentView]                      根视图
     * [duration]                        间隔时间
     * [fullView]                        大图id
     * [thumbnailView]                   缩略图
     * [holder]                          RecyclerView.ViewHolder
     * [listener]                        监听动画开始和结束
     * [afterTransitionListener]         动画结束以后
     */
    fun enterAnimStart(
        parentView: View,
        duration: Long,
        fullView: ImageView,
        thumbnailView: ImageView,
        holder: RecyclerView.ViewHolder,
        listener: OnAnimatorListener,
        afterTransitionListener: OnAfterTransitionListener
    ) {
        AnimEnterHelper.start(
            duration,
            fullView,
            thumbnailView,
            holder,
            listener,
            afterTransitionListener
        )
        //背景颜色变化
        AnimBgHelper.enter(parentView, 0f, duration)
    }
}