package com.starot.larger.anim

import android.os.Build
import android.transition.*
import android.util.Log
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.starot.larger.impl.OnAfterTransitionListener
import com.starot.larger.impl.OnAnimatorIntercept

object AnimDragHelper : OnAnimatorIntercept {
    private var thumbnailView: ImageView? = null


    override fun beforeTransition(
        fullView: ImageView,
        thumbnailView: ImageView?
    ) {
        this.thumbnailView = thumbnailView
    }


    override fun startTransition(
        fullView: ImageView,
        thumbnailView: ImageView?
    ) {
        if (thumbnailView == null) {
            return
        }
        fullView.translationX = (0f)
        fullView.translationY = (0f)
        fullView.scaleX = (1f)
        fullView.scaleY = (1f)
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
    }


}