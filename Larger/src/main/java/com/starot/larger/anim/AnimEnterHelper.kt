package com.starot.larger.anim

import android.transition.ChangeBounds
import android.transition.ChangeImageTransform
import android.transition.Transition
import android.transition.TransitionSet
import android.util.Log
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.recyclerview.widget.RecyclerView
import com.starot.larger.activity.LargerAct
import com.starot.larger.impl.OnAfterTransitionListener
import com.starot.larger.impl.OnAnimatorIntercept

object AnimEnterHelper : OnAnimatorIntercept {


    override fun beforeTransition(
        photoId: Int,
        fullView: ImageView,
        thumbnailView: ImageView
    ) {
        fullView.scaleType = thumbnailView.scaleType
        fullView.layoutParams = fullView.layoutParams.apply {
            width = thumbnailView.width
            height = thumbnailView.height
            val location = getLocationOnScreen(thumbnailView)
            when (fullView.parent) {
                is ConstraintLayout -> {
                    val constraintSet = ConstraintSet().apply {
                        clone(fullView.parent as ConstraintLayout)
                        clear(photoId, ConstraintSet.START)
                        clear(photoId, ConstraintSet.TOP)
                        clear(photoId, ConstraintSet.BOTTOM)
                        clear(photoId, ConstraintSet.RIGHT)
                        //重新建立约束
                        connect(
                            photoId, ConstraintSet.TOP, ConstraintSet.PARENT_ID,
                            ConstraintSet.TOP, location[1]
                        )
                        connect(
                            photoId, ConstraintSet.START, ConstraintSet.PARENT_ID,
                            ConstraintSet.START, location[0]
                        )
                    }
                    constraintSet.applyTo(fullView.parent as ConstraintLayout)
                }
                else -> {
                    if (this is ViewGroup.MarginLayoutParams) {
                        marginStart = location[0]
                        topMargin = location[1]
                    }
                }
            }


        }

    }

    override fun startTransition(fullView: ImageView, thumbnailView: ImageView) {
        fullView.scaleType = ImageView.ScaleType.FIT_CENTER
        fullView.layoutParams = fullView.layoutParams.apply {
            width = ViewGroup.LayoutParams.MATCH_PARENT
            height = ViewGroup.LayoutParams.MATCH_PARENT
            if (this is ViewGroup.MarginLayoutParams) {
                marginStart = 0
                topMargin = 0
            }
        }
    }

    override fun transitionSet(durationTime: Long): Transition {
        return TransitionSet().apply {
            addTransition(ChangeBounds())
            addTransition(ChangeImageTransform())
            duration = durationTime
            interpolator = DecelerateInterpolator()
        }
    }

    override fun afterTransition(
        afterTransitionListener: OnAfterTransitionListener,
        holder: RecyclerView.ViewHolder
    ) {
        Log.i(LargerAct.TAG, "动画加载结束，加载高清大图")
        afterTransitionListener.afterTransitionLoad(holder)
    }


}