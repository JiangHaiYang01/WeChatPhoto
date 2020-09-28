package com.starot.larger.anim

import android.os.Build
import android.transition.*
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import com.starot.larger.anim.impl.OnAnimatorIntercept
import com.starot.larger.anim.impl.OnAnimatorListener
import com.starot.larger.enums.AnimType
import com.starot.larger.impl.OnImageLoadReadyListener
import com.starot.larger.utils.LogUtils

object AnimEnterHelper : OnAnimatorIntercept {


    override fun start(
        type: AnimType,
        duration: Long,
        fullView: View,
        thumbnailView: View?,
        listener: OnAnimatorListener
    ) {
        beforeTransition(
            type,
            fullView,
            fullView,
            thumbnailView,
            listener,
            object : OnImageLoadReadyListener {
                override fun onLoadFailed() {

                }

                override fun onReady() {
                    fullView.post {
                        TransitionManager.beginDelayedTransition(
                            fullView.parent as ViewGroup,
                            getTransition(type, duration, listener)
                        )
                        startTransition(type, fullView, thumbnailView, listener)
                    }
                }
            })


    }

//    override fun beforeTransition(
//        type: AnimType,
//        itemView: View,
//        fullView: View,
//        thumbnailView: View?,
//        listener: OnAnimatorListener
//    ) {
//        if (thumbnailView == null) {
//            LogUtils.i("beforeTransition thumbnailView is null")
//            return
//        }
//        listener.onTranslatorBefore(type, fullView, thumbnailView)
//        fullView.layoutParams = fullView.layoutParams.apply {
//            width = thumbnailView.width
//            height = thumbnailView.height
//            AnimParentHelper.parentAnim(this, thumbnailView, fullView)
//        }
//
//    }

    override fun beforeTransition(
        type: AnimType,
        itemView: View,
        fullView: View,
        thumbnailView: View?,
        listener: OnAnimatorListener,
        onImageLoadReadyListener: OnImageLoadReadyListener?
    ) {
        if (thumbnailView == null) {
            LogUtils.i("beforeTransition thumbnailView is null")
            return
        }
        listener.onTranslatorBefore(type, fullView, thumbnailView, onImageLoadReadyListener)
        fullView.layoutParams = fullView.layoutParams.apply {
            width = thumbnailView.width
            height = thumbnailView.height
            AnimParentHelper.parentAnim(this, thumbnailView, fullView)
        }
    }

    override fun startTransition(
        type: AnimType,
        fullView: View,
        thumbnailView: View?,
        listener: OnAnimatorListener
    ) {
        if (thumbnailView == null) {
            LogUtils.i("startTransition thumbnailView is null")
            return
        }
        listener.onTranslatorStart(type, fullView, thumbnailView)
        fullView.layoutParams = fullView.layoutParams.apply {
            width = ViewGroup.LayoutParams.MATCH_PARENT
            height = ViewGroup.LayoutParams.MATCH_PARENT
            if (this is ViewGroup.MarginLayoutParams) {
                marginStart = 0
                topMargin = 0
            }
        }
    }

    override fun transitionSet(durationTime: Long): Transition {
        return TransitionSet().apply {
            addTransition(ChangeBounds())
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                addTransition(ChangeImageTransform())
            }
            duration = durationTime
            interpolator = DecelerateInterpolator()
        }
    }


}