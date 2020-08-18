package com.starot.larger.anim

import android.os.Build
import android.transition.ChangeBounds
import android.transition.ChangeImageTransform
import android.transition.Transition
import android.transition.TransitionSet
import android.util.Log
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.starot.larger.impl.OnAfterTransitionListener
import com.starot.larger.impl.OnAnimatorIntercept

object AnimEnterHelper : OnAnimatorIntercept {


    override fun beforeTransition(
        fullView: ImageView,
        thumbnailView: ImageView
    ) {
        fullView.scaleType = thumbnailView.scaleType
        fullView.layoutParams = fullView.layoutParams.apply {
            width = thumbnailView.width
            height = thumbnailView.height
            AnimParentHelper.parentAnim(this, thumbnailView, fullView)
        }

    }


    override fun startTransition(
        fullView: ImageView,
        thumbnailView: ImageView
    ) {
        fullView.scaleType = ImageView.ScaleType.FIT_CENTER
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

    override fun afterTransition(
        afterTransitionListener: OnAfterTransitionListener,
        holder: RecyclerView.ViewHolder
    ) {
        afterTransitionListener.afterTransitionLoad(true, holder)
    }


}