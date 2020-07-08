package com.starot.larger.anim

import android.transition.ChangeBounds
import android.transition.ChangeImageTransform
import android.transition.Transition
import android.transition.TransitionSet
import android.util.Log
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import com.starot.larger.activity.LargerAct
import com.starot.larger.impl.OnAnimatorIntercept

object AnimEnterHelper : OnAnimatorIntercept {


    override fun beforeTransition(fullView: ImageView, thumbnailView: ImageView) {
        Log.i(LargerAct.TAG,"小图 --->  大图 动画 beforeTransition")
        fullView.scaleType = thumbnailView.scaleType
        fullView.layoutParams = fullView.layoutParams.apply {
            width = thumbnailView.width
            height = thumbnailView.height
            val location = getLocationOnScreen(thumbnailView)
            if (this is ViewGroup.MarginLayoutParams) {
                marginStart = location[0]
                topMargin = location[1]
            }
        }

    }

    override fun startTransition(fullView: ImageView, thumbnailView: ImageView) {
        fullView.scaleType = ImageView.ScaleType.FIT_CENTER
        fullView.layoutParams= fullView.layoutParams.apply {
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
            addTransition(ChangeImageTransform())
            duration = durationTime
            interpolator = DecelerateInterpolator()
        }
    }

    override fun afterTransition() {
    }
}