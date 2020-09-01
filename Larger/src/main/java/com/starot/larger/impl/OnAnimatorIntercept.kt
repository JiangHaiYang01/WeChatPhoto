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
        beforeListener: OnBeforeTransitionListener?,
        afterTransitionListener: OnAfterTransitionListener
    ) {

        beforeTransition(holder.itemView, fullView, thumbnailView, beforeListener)

        holder.itemView.postDelayed(
            {
                TransitionManager.beginDelayedTransition(
                    holder.itemView as ViewGroup,
                    getTransition(duration, listener, afterTransitionListener, holder)
                )
                startTransition(fullView, thumbnailView)
            }, 50)
    }

    fun start(
        duration: Long,
        fullView: View,
        thumbnailView: ImageView?,
        holder: RecyclerView.ViewHolder,
        listener: OnAnimatorListener,
        afterTransitionListener: OnAfterTransitionListener
    ) {
        start(
            duration,
            fullView,
            thumbnailView,
            holder, listener, null, afterTransitionListener
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
        itemView: View,
        fullView: View,
        thumbnailView: ImageView?,
        beforeListener: OnBeforeTransitionListener?
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