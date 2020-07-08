package com.starot.larger.anim

import android.animation.Animator
import android.transition.*
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import com.starot.larger.adapter.ViewPagerAdapter
import com.starot.larger.tools.ImageTool
import com.starot.larger.view.image.PhotoView

object TransitionExitAnimHelper {


    fun start(
        durationTime: Long,
        photoViewId: Int,
        holder: ViewPagerAdapter.PhotoViewHolder,
        index: Int,
        animatorListener: Animator.AnimatorListener?
    ) {
        val fullView = holder.itemView.findViewById<PhotoView>(photoViewId)
        val thumbnailView = LargerAnim.imageViews[index]

        holder.itemView.postDelayed({
            Log.i(ImageTool.TAG, "exit =========================beginDelayedTransition")
            TransitionManager.beginDelayedTransition(
                holder.itemView as ViewGroup,
                transitionSet(durationTime).also {
                    it.addListener(object : Transition.TransitionListener {
                        override fun onTransitionEnd(transition: Transition?) {
                            afterTransition(holder)
                            animatorListener?.onAnimationEnd(null)
                        }

                        override fun onTransitionResume(transition: Transition?) {
                        }

                        override fun onTransitionPause(transition: Transition?) {
                        }

                        override fun onTransitionCancel(transition: Transition?) {
                        }

                        override fun onTransitionStart(transition: Transition?) {
                            animatorListener?.onAnimationStart(null)
                        }
                    })
                }
            )
            transition(fullView, thumbnailView)
        }, 50)
    }

    private fun afterTransition(holder: ViewPagerAdapter.PhotoViewHolder) {

    }

    private fun transition(
        photoView: PhotoView,
        thumbnailView: ImageView
    ) {
        photoView.scaleType = thumbnailView.scaleType
        photoView.translationX = 0f
        photoView.translationY = 0f
        photoView.scaleX = 1f
        photoView.scaleY = 1f
        photoView.layoutParams = photoView.layoutParams.apply {
            width = thumbnailView.width
            height = thumbnailView.height
            val location = IntArray(2)
            getLocationOnScreen(location, thumbnailView)
            if (this is ViewGroup.MarginLayoutParams) {
                marginStart = location[0]
                topMargin = location[1]
            }
        }
    }


    private fun getLocationOnScreen(location: IntArray, thumbnailView: ImageView) {
        thumbnailView.getLocationOnScreen(location)
        Log.i(ImageTool.TAG, "当前控件相对于屏幕左上角位置 ${location[0]}  ${location[1]}")
    }

    private fun transitionSet(durationTime: Long): Transition {
        return TransitionSet().apply {
            addTransition(ChangeBounds())
            addTransition(ChangeImageTransform())
            addTransition(ChangeTransform())
            duration = durationTime
            interpolator = DecelerateInterpolator()
        }
    }

}