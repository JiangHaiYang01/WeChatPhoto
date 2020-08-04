package com.starot.larger.anim

import android.os.Build
import android.transition.*
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.starot.larger.impl.OnAfterTransitionListener
import com.starot.larger.impl.OnAnimatorIntercept

object AnimDragHelper : OnAnimatorIntercept {


    override fun beforeTransition(
        photoId: Int,
        fullView: ImageView,
        thumbnailView: ImageView
    ) {

    }

    override fun startTransition(
        photoId: Int,
        fullView: ImageView,
        thumbnailView: ImageView
    ) {
        fullView.translationX = (0f)
        fullView.translationY = (0f)
        fullView.scaleX = (1f)
        fullView.scaleY = (1f)
    }

    override fun transitionSet(durationTime: Long): Transition {

        return TransitionSet().apply {
            addTransition(ChangeBounds())
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                addTransition(ChangeImageTransform())
                addTransition(ChangeTransform())
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