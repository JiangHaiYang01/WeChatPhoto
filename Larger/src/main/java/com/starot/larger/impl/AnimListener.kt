package com.starot.larger.impl

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.starot.larger.anim.AnimBgHelper
import com.starot.larger.anim.AnimEnterHelper
import com.starot.larger.anim.AnimExitHelper

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
     */
    fun enterAnimStart(
        parentView: View,
        duration: Long,
        fullView: ImageView,
        thumbnailView: ImageView,
        holder: RecyclerView.ViewHolder
    ) {
        AnimEnterHelper.start(
            duration,
            fullView,
            thumbnailView,
            holder,
            object : OnAnimatorListener {
                override fun onAnimatorStart() {
                    onEnterAnimStart()
                }

                override fun onAnimatorEnd() {
                    onEnterAnimEnd()
                }
            },
            object : OnAfterTransitionListener {
                override fun afterTransitionLoad(
                    isLoadFull: Boolean,
                    holder: RecyclerView.ViewHolder
                ) {

                }
            }
        )
        //背景颜色变化
        AnimBgHelper.enter(parentView, 0f, duration)
    }

    fun exitAnimStart(
        parentView: View,
        duration: Long,
        fullView: ImageView,
        thumbnailView: ImageView,
        holder: RecyclerView.ViewHolder
    ) {
        AnimExitHelper.start(
            duration,
            fullView,
            thumbnailView,
            holder,
            object : OnAnimatorListener {
                override fun onAnimatorStart() {
                    onExitAnimStart()
                }

                override fun onAnimatorEnd() {
                    onExitAnimEnd()
                }
            },
            object : OnAfterTransitionListener {
                override fun afterTransitionLoad(
                    isLoadFull: Boolean,
                    holder: RecyclerView.ViewHolder
                ) {

                }
            }
        )
        //背景颜色变化
        AnimBgHelper.exit(parentView, 1.0f, duration)

    }


    fun onEnterAnimStart()

    fun onEnterAnimEnd()

    fun onExitAnimStart()

    fun onExitAnimEnd()
}