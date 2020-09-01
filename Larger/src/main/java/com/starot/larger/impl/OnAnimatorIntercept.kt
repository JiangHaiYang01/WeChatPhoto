package com.starot.larger.impl

import android.transition.Transition
import android.transition.TransitionManager
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.starot.larger.Larger
import com.starot.larger.enums.FullType

interface OnAnimatorIntercept {


    fun start(
        duration: Long,
        fullView: View,
        thumbnailView: ImageView?,
        holder: RecyclerView.ViewHolder,
        listener: OnAnimatorListener,
        afterTransitionListener: OnAfterTransitionListener
    ) {

        beforeTransition(fullView, thumbnailView)

        holder.itemView.postDelayed(
            {
                TransitionManager.beginDelayedTransition(
                    holder.itemView as ViewGroup,
                    getTransition(duration, listener, afterTransitionListener, holder)
                )
                startTransition(fullView, thumbnailView)
            }, if (Larger.type == FullType.Image) {
                50
            } else {
                1000
            }
        )
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
        fullView: View,
        thumbnailView: ImageView?
    )


    fun startTransition(
        fullView: View,
        thumbnailView: ImageView?
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