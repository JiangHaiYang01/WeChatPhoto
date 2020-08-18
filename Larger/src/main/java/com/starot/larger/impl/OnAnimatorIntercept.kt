package com.starot.larger.impl

import android.transition.Transition
import android.transition.TransitionManager
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

interface OnAnimatorIntercept {


    fun start(
        duration: Long,
        fullView: ImageView,
        thumbnailView: ImageView,
        holder: RecyclerView.ViewHolder,
        listener: OnAnimatorListener,
        afterTransitionListener: OnAfterTransitionListener
    ) {

        beforeTransition(fullView, thumbnailView)

        holder.itemView.postDelayed({
            TransitionManager.beginDelayedTransition(
                holder.itemView as ViewGroup,
                getTransition(duration, listener, afterTransitionListener, holder)
            )
            startTransition(fullView, thumbnailView)
        }, 50)
    }

    fun getTransition(
        duration: Long,
        listener: OnAnimatorListener,
        afterTransitionListener: OnAfterTransitionListener,
        holder: RecyclerView.ViewHolder
    ): Transition {
        return transitionSet(duration).apply {
            addListener(object : Transition.TransitionListener {
                override fun onTransitionEnd(transition: Transition?) {
                    listener.onAnimatorEnd()
                    afterTransition(afterTransitionListener, holder)
                }

                override fun onTransitionResume(transition: Transition?) {
                }

                override fun onTransitionPause(transition: Transition?) {
                }

                override fun onTransitionCancel(transition: Transition?) {
                }

                override fun onTransitionStart(transition: Transition?) {
                    listener.onAnimatorStart()
                }
            })
        }
    }

    fun beforeTransition(
        fullView: ImageView,
        thumbnailView: ImageView
    )


    fun startTransition(
        fullView: ImageView,
        thumbnailView: ImageView
    )

    fun transitionSet(durationTime: Long): Transition

    fun afterTransition(
        afterTransitionListener: OnAfterTransitionListener,
        holder: RecyclerView.ViewHolder
    )

    fun getLocationOnScreen(thumbnailView: ImageView): IntArray {
        val location = IntArray(2)
        thumbnailView.getLocationOnScreen(location)
        return location
    }
}