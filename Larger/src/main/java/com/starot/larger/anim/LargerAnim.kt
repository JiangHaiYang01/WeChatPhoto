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

object LargerAnim {


    fun startExit(
        context: Context,
        parent: View,
        target: View,
        originalScale: Float,
        info: ImageInfo,
        duration: Long,
        animatorListener: Animator.AnimatorListener
    ) {
        startExitParentAnim(parent, originalScale, duration)
//        parent.setBackgroundColor(ColorTool.getColorWithAlpha(Color.BLACK, 0f))
        startExitViewScaleAnim(context, target, originalScale, info, duration, animatorListener)
    }


    fun startEnter(
        context: Context,
        parent: View,
        target: View,
        originalScale: Float,
        info: ImageInfo,
        duration: Long,
        animatorListener: Animator.AnimatorListener?
    ) {
        startEnterParentAnim(parent, originalScale, duration)
        startEnterViewScaleAnim(context, target, originalScale, info, duration, animatorListener)
    }

    //修改进入的时候背景 渐变 黑色
    private fun startExitParentAnim(
        parent: View,
        originalScale: Float,
        duration: Long
    ) {
        val valueAnimator = ValueAnimator()
        valueAnimator.duration = duration
        valueAnimator.setFloatValues(1f, originalScale)
        valueAnimator.addUpdateListener { animation ->
            parent.setBackgroundColor(
                ColorTool.getColorWithAlpha(Color.BLACK, (animation.animatedValue as Float))
            )
        }
        valueAnimator.start()
    }


    //修改进入的时候背景 渐变 黑色
    private fun startEnterParentAnim(
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


    private fun startExitViewScaleAnim(
        context: Context,
        target: View,
        originalScale: Float,
        info: ImageInfo,
        duration: Long,
        animatorListener: Animator.AnimatorListener
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
            target.scaleX,
            originalScale
        )
        val animatorY = ObjectAnimator.ofFloat(
            target, View.SCALE_Y,
            target.scaleY,
            originalScale
        )
        val animatorTransX: ObjectAnimator = ObjectAnimator.ofFloat(
            target,
            View.TRANSLATION_X,
            target.translationX + (ImageTool.getWindowWidth(context) / 2 * (1 - target.scaleX) - target.pivotX * (1 - target.scaleX)),
            0f
        )
        val animatorTransY: ObjectAnimator = ObjectAnimator.ofFloat(
            target,
            View.TRANSLATION_Y,
            target.translationY + (ImageTool.getWindowHeight(context) / 2 * (1 - target.scaleY) - target.pivotY * (1 - target.scaleY)),
            0f
        )
        val set = AnimatorSet()
        set.playTogether(animatorX, animatorY, animatorTransX, animatorTransY)
        set.duration = duration
        set.start()
        set.addListener(animatorListener)
    }

    //小图片 到 大图的动画
    private fun startEnterViewScaleAnim(
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
        set.start()
        if (animatorListener != null)
            set.addListener(animatorListener)
    }
}