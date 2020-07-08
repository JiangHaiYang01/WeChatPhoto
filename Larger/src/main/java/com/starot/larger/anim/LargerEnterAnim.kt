package com.starot.larger.anim

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Color
import android.view.View
import com.starot.larger.bean.ImageInfo
import com.starot.larger.tools.ColorTool
import com.starot.larger.tools.ImageTool

object LargerEnterAnim {
    //修改进入的时候背景 渐变 黑色
    fun startEnterParentAnim(
        parent: View,
        originalScale: Float,
        duration: Long
    ) {
        val valueAnimator = ValueAnimator()
        valueAnimator.duration = duration
        valueAnimator.setFloatValues(originalScale, 1f)
        valueAnimator.addUpdateListener { animation ->
            parent.setBackgroundColor(
                ColorTool.getColorWithAlpha(Color.BLACK, (animation.animatedValue as Float))
            )
        }
        valueAnimator.start()
    }

    //小图片 到 大图的动画
    fun startEnterViewScaleAnim(
        context: Context,
        target: View,
        originalScale: Float,
        info: ImageInfo,
        duration: Long,
        animatorListener: Animator.AnimatorListener?
    ) {

        val pivotX: Float
        val pivotY: Float
        val animImgStartHeight: Float
        val animImgStartWidth: Float
        val width: Float = info.width
        val height: Float = info.height
        val localX: Float = info.left
        val localY: Float = info.top
        val windowScale: Float = ImageTool.getWindowScale(context)
        val imgScale: Float = ImageTool.getImgScale(width, height)
        if (imgScale >= windowScale) {
            animImgStartHeight = ImageTool.getWindowHeight(context) * originalScale
            pivotX = localX / (1 - originalScale)
            pivotY = (localY - (animImgStartHeight - height) / 2) / (1 - originalScale)
        } else {
            animImgStartWidth = ImageTool.getWindowWidth(context) * originalScale
            pivotX = (localX - (animImgStartWidth - width) / 2) / (1 - originalScale)
            pivotY = localY / (1 - originalScale)
        }
        target.pivotX = pivotX
        target.pivotY = pivotY
        val animatorX = ObjectAnimator.ofFloat(
            target, View.SCALE_X,
            originalScale,
            1.0f
        )
        val animatorY = ObjectAnimator.ofFloat(
            target, View.SCALE_Y,
            originalScale,
            1.0f
        )
        val set = AnimatorSet()
        set.playTogether(animatorX, animatorY)
        set.duration = duration
        if (animatorListener != null)
            set.addListener(animatorListener)
        set.start()

    }
}