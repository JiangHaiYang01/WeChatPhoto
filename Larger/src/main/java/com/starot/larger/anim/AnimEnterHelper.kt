package com.starot.larger.anim

import android.os.Build
import android.transition.ChangeBounds
import android.transition.ChangeImageTransform
import android.transition.Transition
import android.transition.TransitionSet
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import com.starot.larger.anim.impl.OnAnimatorIntercept
import com.starot.larger.anim.impl.OnAnimatorListener
import com.starot.larger.utils.LogUtils

object AnimEnterHelper : OnAnimatorIntercept {


    override fun beforeTransition(
        itemView: View,
        fullView: View,
        thumbnailView: View?,
        listener: OnAnimatorListener
    ) {
        if (thumbnailView == null) {
            LogUtils.i("beforeTransition thumbnailView is null")
            return
        }
        listener.onTranslatorBefore(fullView, thumbnailView)
        fullView.layoutParams = fullView.layoutParams.apply {
            width = thumbnailView.width
            height = thumbnailView.height
            AnimParentHelper.parentAnim(this, thumbnailView, fullView)
        }

    }


    override fun startTransition(
        fullView: View,
        thumbnailView: View?,
        listener: OnAnimatorListener
    ) {
        if (thumbnailView == null) {
            LogUtils.i("startTransition thumbnailView is null")
            return
        }
        listener.onTranslatorStart(fullView, thumbnailView)
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