package com.starot.larger.anim

import android.os.Build
import android.transition.*
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.starot.larger.anim.impl.OnAnimatorIntercept
import com.starot.larger.anim.impl.OnAnimatorListener
import com.starot.larger.enums.AnimType
import com.starot.larger.utils.LogUtils

object AnimExitHelper : OnAnimatorIntercept {

    private var thumbnailView: View? = null

    override fun beforeTransition(
        type: AnimType,
        itemView: View,
        fullView: View,
        thumbnailView: View?,
        listener: OnAnimatorListener
    ) {
        this.thumbnailView = thumbnailView
        if (thumbnailView == null) {
            LogUtils.i("beforeTransition thumbnailView is null")
            return
        }
        listener.onTranslatorBefore(type, fullView, thumbnailView)

    }


    override fun startTransition(
        type: AnimType,
        fullView: View,
        thumbnailView: View?,
        listener: OnAnimatorListener
    ) {
        if (thumbnailView == null) {
            LogUtils.i("startTransition thumbnailView is null")
            return
        }
        listener.onTranslatorStart(type, fullView, thumbnailView)
        fullView.translationX = 0f
        fullView.translationY = 0f
        fullView.scaleX = 1f
        fullView.scaleY = 1f
        fullView.layoutParams = fullView.layoutParams.apply {
            width = thumbnailView.width
            height = thumbnailView.height
            AnimParentHelper.parentAnim(this, thumbnailView, fullView)
        }
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


}