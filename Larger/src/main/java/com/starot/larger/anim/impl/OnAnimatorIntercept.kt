package com.starot.larger.anim.impl


import android.transition.Transition
import android.transition.TransitionManager
import android.view.View
import android.view.ViewGroup
import com.starot.larger.enums.AnimType


interface OnAnimatorListener {


    //变化开始之前
    fun onTranslatorBefore(
        type: AnimType,
        fullView: View,
        thumbnailView: View
    )

    //开始变化
    fun onTranslatorStart(
        type: AnimType,
        fullView: View,
        thumbnailView: View
    )

    //动画开始
    fun onAnimatorStart(type: AnimType)

    //动画结束
    fun onAnimatorEnd(type: AnimType)

    //动画取消
    fun onAnimatorCancel(type: AnimType)


}


interface OnAnimatorIntercept {


    fun start(
        type: AnimType,
        duration: Long,
        fullView: View,
        thumbnailView: View?,
        listener: OnAnimatorListener
    ) {

        beforeTransition(type, fullView, fullView, thumbnailView, listener)

        fullView.postDelayed(
            {
                TransitionManager.beginDelayedTransition(
                    fullView.parent as ViewGroup,
                    getTransition(type, duration, listener)
                )
                startTransition(type, fullView, thumbnailView, listener)
            }, 50
        )
    }


    fun getTransition(
        type: AnimType,
        duration: Long,
        listener: OnAnimatorListener
    ): Transition {
        return transitionSet(duration).apply {
            addListener(object : Transition.TransitionListener {
                override fun onTransitionEnd(transition: Transition?) {
                    listener.onAnimatorEnd(type)
                }

                override fun onTransitionResume(transition: Transition?) {
                }

                override fun onTransitionPause(transition: Transition?) {
                }

                override fun onTransitionCancel(transition: Transition?) {
                    listener.onAnimatorCancel(type)
                }

                override fun onTransitionStart(transition: Transition?) {
                    listener.onAnimatorStart(type)
                }
            })
        }
    }

    fun beforeTransition(
        type: AnimType,
        itemView: View,
        fullView: View,
        thumbnailView: View?,
        listener: OnAnimatorListener
    )


    fun startTransition(
        type: AnimType,
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