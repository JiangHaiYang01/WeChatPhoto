package com.starot.larger.anim

import android.transition.*
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.starot.larger.impl.OnAfterTransitionListener
import com.starot.larger.impl.OnAnimatorIntercept

object AnimExitHelper : OnAnimatorIntercept {
    override fun beforeTransition(
        photoId: Int,
        fullView: ImageView,
        thumbnailView: ImageView
    ) {

    }

    override fun startTransition(fullView: ImageView, thumbnailView: ImageView) {
        fullView.scaleType = thumbnailView.scaleType
        fullView.translationX = 0f
        fullView.translationY = 0f
        fullView.scaleX = 1f
        fullView.scaleY = 1f
        fullView.layoutParams = fullView.layoutParams.apply {
            width = thumbnailView.width
            height = thumbnailView.height
            val location = AnimEnterHelper.getLocationOnScreen(thumbnailView)
            if (this is ViewGroup.MarginLayoutParams) {
                marginStart = location[0]
                topMargin = location[1]
            }
        }
    }

    override fun transitionSet(durationTime: Long): Transition {
        return TransitionSet().apply {
            addTransition(ChangeBounds())
            addTransition(ChangeImageTransform())
            addTransition(ChangeTransform())
            duration = durationTime
            interpolator = DecelerateInterpolator()
        }
    }

    override fun afterTransition(
        afterTransitionListener: OnAfterTransitionListener,
        holder: RecyclerView.ViewHolder
    ) {
    }
}