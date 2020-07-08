package com.starot.larger.anim

import android.animation.Animator
import android.content.Context
import android.view.View
import android.widget.ImageView
import com.starot.larger.adapter.ViewPagerAdapter
import com.starot.larger.bean.ImageInfo

object LargerAnim {

    var imageViews = ArrayList<ImageView>()

    fun dragFinish(
        parent: View,
        target: View,
        originalScale: Float,
        duration: Long
    ) {
        LargerEnterAnim.startEnterParentAnim(parent, originalScale, duration)
        LargerDragAnim.translationDrag(duration, originalScale, target)
    }


    fun startExitDrag(
        context: Context,
        parent: View,
        target: View,
        originalScale: Float,
        currentScale: Float,
        info: ImageInfo,
        duration: Long,
        animatorListener: Animator.AnimatorListener
    ) {
        LargerDragAnim.startExitParentAnimDrag(currentScale, parent, originalScale, duration)
        LargerExitAnim.startExitViewScaleAnim(
            context,
            target,
            originalScale,
            info,
            duration,
            animatorListener
        )
    }

    fun startExit(
        context: Context,
        parent: View,
        target: View,
        originalScale: Float,
        info: ImageInfo,
        duration: Long,
        animatorListener: Animator.AnimatorListener
    ) {
        LargerExitAnim.startExitParentAnim(parent, originalScale, duration)
        LargerExitAnim.startExitViewScaleAnim(
            context,
            target,
            originalScale,
            info,
            duration,
            animatorListener
        )
    }


    //小图 ---> 大图 动画
    fun startEnter(
        context: Context,
        parent: View,
        target: View,
        originalScale: Float,
        info: ImageInfo,
        duration: Long,
        animatorListener: Animator.AnimatorListener?
    ) {
        LargerEnterAnim.startEnterParentAnim(parent, originalScale, duration)
        LargerEnterAnim.startEnterViewScaleAnim(
            context,
            target,
            originalScale,
            info,
            duration,
            animatorListener
        )
    }

    fun startEnter(
        parent: View,
        originalScale: Float,
        duration: Long,
        photoViewId: Int,
        holder: ViewPagerAdapter.PhotoViewHolder,
        index: Int,
        animatorListener: Animator.AnimatorListener?
    ) {
        LargerEnterAnim.startEnterParentAnim(parent, originalScale, duration)
        TransitionEnterAnimHelper.start(duration, photoViewId, holder, index,animatorListener)
    }


    fun startExit(
        parent: View,
        originalScale: Float,
        duration: Long,
        photoViewId: Int,
        holder: ViewPagerAdapter.PhotoViewHolder,
        index: Int,
        animatorListener: Animator.AnimatorListener?
    ) {
        LargerExitAnim.startExitParentAnim(parent, originalScale, duration)
        TransitionExitAnimHelper.start(duration, photoViewId, holder, index,animatorListener)
    }


}