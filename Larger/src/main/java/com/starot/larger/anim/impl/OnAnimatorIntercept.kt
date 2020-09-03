package com.starot.larger.anim.impl


import android.transition.Transition
import android.transition.TransitionManager
import android.view.View
import android.view.ViewGroup


interface OnAnimatorListener {


    //变化开始之前
    fun onTranslatorBefore(
        fullView: View,
        thumbnailView: View
    )

    //开始变化
    fun onTranslatorStart( fullView: View,
                           thumbnailView: View)

    //动画开始
    fun onAnimatorStart()

    //动画结束
    fun onAnimatorEnd()

    //动画取消
    fun onAnimatorCancel()


}


interface OnAnimatorIntercept {


    fun start(
        duration: Long,
        fullView: View,
        thumbnailView: View?,
        listener: OnAnimatorListener
    ) {

        beforeTransition(fullView, fullView, thumbnailView, listener)

        fullView.postDelayed(
            {
                TransitionManager.beginDelayedTransition(
                    fullView.parent as ViewGroup,
                    getTransition(duration, listener)
                )
                startTransition(fullView, thumbnailView,listener)
            }, 50
        )
    }


    fun getTransition(
        duration: Long,
        listener: OnAnimatorListener
    ): Transition {
        return transitionSet(duration).apply {
            addListener(object : Transition.TransitionListener {
                override fun onTransitionEnd(transition: Transition?) {
                    listener.onAnimatorEnd()
                }

                override fun onTransitionResume(transition: Transition?) {
                }

                override fun onTransitionPause(transition: Transition?) {
                }

                override fun onTransitionCancel(transition: Transition?) {
                    listener.onAnimatorCancel()
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
        thumbnailView: View?,
        listener: OnAnimatorListener
    )


    fun startTransition(
        fullView: View,
        thumbnailView: View?,
        listener: OnAnimatorListener
    )

    fun transitionSet(durationTime: Long): Transition


    fun getLocationOnScreen(thumbnailView: View): IntArray {
        val location = IntArray(2)
        thumbnailView.getLocationOnScreen(location)
        return location
    }
}