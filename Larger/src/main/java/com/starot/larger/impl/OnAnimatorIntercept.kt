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
        listener: OnAnimatorListener
    ) {
        beforeTransition(fullView, thumbnailView)

        holder.itemView.postDelayed({
            TransitionManager.beginDelayedTransition(
                holder.itemView as ViewGroup,
                transitionSet(duration).also {
                    it.addListener(object : Transition.TransitionListener {
                        override fun onTransitionEnd(transition: Transition?) {
                            listener.OnAnimatorEnd()
                            afterTransition()
                        }

                        override fun onTransitionResume(transition: Transition?) {
                        }

                        override fun onTransitionPause(transition: Transition?) {
                        }

                        override fun onTransitionCancel(transition: Transition?) {
                        }

                        override fun onTransitionStart(transition: Transition?) {
                            listener.OnAnimatorStart()
                        }
                    })
                }
            )
            startTransition(fullView, thumbnailView)
        }, 50)
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

    fun afterTransition()

    fun getLocationOnScreen(thumbnailView: ImageView): IntArray {
        val location = IntArray(2)
        thumbnailView.getLocationOnScreen(location)
        return location
    }
}