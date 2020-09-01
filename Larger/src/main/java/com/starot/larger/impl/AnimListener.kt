package com.starot.larger.impl

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.starot.larger.Larger
import com.starot.larger.anim.AnimBgHelper
import com.starot.larger.anim.AnimDragHelper
import com.starot.larger.anim.AnimEnterHelper
import com.starot.larger.anim.AnimExitHelper
import com.starot.larger.enums.FullType
import com.starot.larger.utils.LogUtils

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
        fullView: View,
        thumbnailView: ImageView?,
        holder: RecyclerView.ViewHolder
    ) {
        LogUtils.i("小图到大图的动画 start")
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
            //开始之前
            object : OnBeforeTransitionListener {
                override fun onBeforeTransitionLoad(
                    itemView: View,
                    fullView: View,
                    thumbnailView: ImageView?
                ) {
                    // 视屏模式
                    if (Larger.type == FullType.Audio) {
                        if (thumbnailView?.drawable != null) {
                            onAudioThumbnail(itemView,thumbnailView.drawable)
                        } else {
                            LogUtils.i("视屏模式 没有设置预览图")
                        }

                    }
                }
            },
            object : OnAfterTransitionListener {
                override fun onAfterTransitionLoad(
                    isLoadFull: Boolean,
                    holder: RecyclerView.ViewHolder
                ) {
                    if (Larger.type == FullType.Image) {
                        LogUtils.i("图片模式 进场动画结束以后 加载大图")
                        onReLoadFullImage(holder)
                    } else if (Larger.type == FullType.Audio) {
                        LogUtils.i("视屏模式 进场动画结束以后 加载视频")
                        onLoadAudio(holder)
                    }

                }
            }
        )
        //背景颜色变化
        AnimBgHelper.enter(parentView, 0f, duration)
    }

    /***
     *
     * 大图到小图的动画逻辑
     *
     * [parentView]                      根视图
     * [duration]                        间隔时间
     * [fullView]                        大图id
     * [thumbnailView]                   缩略图
     * [holder]                          RecyclerView.ViewHolder
     */
    fun exitAnimStart(
        parentView: View,
        duration: Long,
        fullView: View,
        thumbnailView: ImageView?,
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
            //开始之前
            object : OnBeforeTransitionListener {
                override fun onBeforeTransitionLoad(
                    itemView: View,
                    fullView: View,
                    thumbnailView: ImageView?
                ) {
                    // 视屏模式
                    if (Larger.type == FullType.Audio) {
                        onStopVideo()
                    }
                }
            },

            //结束以后
            object : OnAfterTransitionListener {
                override fun onAfterTransitionLoad(
                    isLoadFull: Boolean,
                    holder: RecyclerView.ViewHolder
                ) {
                }
            }
        )
        //背景颜色变化
        AnimBgHelper.exit(parentView, 1.0f, duration)
    }


    /***
     *
     * 移动过程中 退出
     *
     * [start]                           开始状态
     * [parentView]                      根视图
     * [duration]                        间隔时间
     * [fullView]                        大图id
     * [thumbnailView]                   缩略图
     * [holder]                          RecyclerView.ViewHolder
     */
    fun dragExitAnimStart(
        start: Float,
        parentView: View,
        duration: Long,
        fullView: ImageView,
        thumbnailView: ImageView?,
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
                override fun onAfterTransitionLoad(
                    isLoadFull: Boolean,
                    holder: RecyclerView.ViewHolder
                ) {

                }
            }
        )
        //背景颜色变化
        AnimBgHelper.exit(parentView, start, duration)
    }


    /***
     *
     * 移动过程中 还原
     *
     * [start]                           开始状态
     * [parentView]                      根视图
     * [duration]                        间隔时间
     * [fullView]                        大图id
     * [thumbnailView]                   缩略图
     * [holder]                          RecyclerView.ViewHolder
     */
    fun dragResumeAnimStart(
        start: Float,
        parentView: View,
        duration: Long,
        fullView: ImageView,
        thumbnailView: ImageView?,
        holder: RecyclerView.ViewHolder
    ) {
        AnimDragHelper.start(
            duration,
            fullView,
            thumbnailView,
            holder,
            object : OnAnimatorListener {
                override fun onAnimatorStart() {
                }

                override fun onAnimatorEnd() {
                }
            },
            object : OnAfterTransitionListener {
                override fun onAfterTransitionLoad(
                    isLoadFull: Boolean,
                    holder: RecyclerView.ViewHolder
                ) {

                }
            }
        )
        //背景颜色变化
        AnimBgHelper.enter(parentView, start, duration)
    }

    //加载视屏
    fun onLoadAudio(holder: RecyclerView.ViewHolder)

    //设置视屏预览图
    fun onAudioThumbnail(itemView: View, drawable: Drawable)

    //停止播放视屏
    fun onStopVideo()

    //重新加载大图
    fun onReLoadFullImage(holder: RecyclerView.ViewHolder)

    //进入的动画开始
    fun onEnterAnimStart()

    //进入动画结束
    fun onEnterAnimEnd()

    //退出的动画开始
    fun onExitAnimStart()

    //退出动画结束
    fun onExitAnimEnd()
}