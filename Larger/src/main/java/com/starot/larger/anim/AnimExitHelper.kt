package com.starot.larger.anim

import android.os.Build
import android.transition.*
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.starot.larger.impl.OnAfterTransitionListener
import com.starot.larger.impl.OnAnimatorIntercept
import com.starot.larger.impl.OnBeforeTransitionListener

object AnimExitHelper : OnAnimatorIntercept {

    private var thumbnailView: ImageView? = null

    override fun beforeTransition(
        fullView: View,
        thumbnailView: ImageView?,
        beforeListener: OnBeforeTransitionListener?
    ) {
        this.thumbnailView = thumbnailView
        beforeListener?.onBeforeTransitionLoad(fullView, thumbnailView)
    }

    override fun startTransition(
        fullView: View,
        thumbnailView: ImageView?
    ) {
        if (thumbnailView == null) {
            fullView.visibility = View.GONE
            return
        }
        if (fullView is ImageView)
            fullView.scaleType = thumbnailView.scaleType
        fullView.translationX = 0f
        fullView.translationY = 0f
        fullView.scaleX = 1f
        fullView.scaleY = 1f
        fullView.layoutParams = fullView.layoutParams.apply {
            width = thumbnailView.width
            height = thumbnailView.height
            AnimParentHelper.parentAnim(this, thumbnailView, fullView)
        }
    }

    override fun transitionSet(durationTime: Long): Transition {
        return TransitionSet().apply {
            if (thumbnailView == null) {
                addTransition(AutoTransition())
            } else {
                addTransition(ChangeBounds())
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    addTransition(ChangeImageTransform())
                    addTransition(ChangeTransform())
                }
            }
            duration = durationTime
            interpolator = DecelerateInterpolator()
        }
    }

    override fun afterTransition(
        afterTransitionListener: OnAfterTransitionListener,
        holder: RecyclerView.ViewHolder
    ) {
        afterTransitionListener.onAfterTransitionLoad(false, holder)
    }
}