package com.starot.larger.utils

import android.content.Context
import com.starot.larger.adapter.ViewPagerAdapter
import com.starot.larger.impl.OnDragAnimListener
import com.starot.larger.view.image.OnViewDragListener
import com.starot.larger.view.image.PhotoView
import kotlin.math.abs

object Drag {

    //是否正在移动
    private var isDragIng = false

    //是否正在动画
    var isAnimIng = false

    var currentScale: Float = 0f

    //开始
    fun startDrag(
        context: Context,
        image: PhotoView,
        holder: ViewPagerAdapter.PhotoViewHolder,
        listener: OnDragAnimListener
    ) {
        image.setOnViewDragListener(object : OnViewDragListener {
            override fun onDrag(dx: Float, dy: Float) {
            }

            override fun onScroll(x: Float, y: Float) {
                //这里 需要判断一下滑动的角度  防止和 viewpager 滑动冲突
                if (isDragIng) {
                    //正在播放动画 不给滑动
                    if (isAnimIng) {
                        return
                    }
                    //图片处于缩放状态
                    if (image.scale != 1.0f) {
                        return
                    }
                    listener.startDrag(context, image, x, y)
                } else {
                    if (abs(x) > 30 && abs(y) > 60) {
                        //正在播放动画 不给滑动
                        if (isAnimIng) {
                            return
                        }
                        //图片处于缩放状态
                        if (image.scale != 1.0f) {
                            return
                        }
                        // 一开始向上滑动无效的
                        if (y > 0) {
                            isDragIng = true
                            listener.startDrag(context, image, x, y)
                        }
                    }
                }
            }

            override fun onScrollFinish() {
                if (isDragIng)
                    listener.stopDrag(image, holder)
            }

            override fun onScrollStart() {
                isDragIng = false
            }
        })
    }
}