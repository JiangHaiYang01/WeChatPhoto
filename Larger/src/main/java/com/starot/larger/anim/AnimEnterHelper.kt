package com.starot.larger.anim

import android.os.Build
import android.transition.ChangeBounds
import android.transition.ChangeImageTransform
import android.transition.Transition
import android.transition.TransitionSet
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import android.widget.VideoView
import androidx.recyclerview.widget.RecyclerView
import com.starot.larger.Larger
import com.starot.larger.enums.FullType
import com.starot.larger.impl.OnAfterTransitionListener
import com.starot.larger.impl.OnAnimatorIntercept
import com.starot.larger.impl.OnBeforeTransitionListener
import com.starot.larger.utils.LogUtils

object AnimEnterHelper : OnAnimatorIntercept {


    override fun beforeTransition(
        itemView: View,
        fullView: View,
        thumbnailView: ImageView?,
        beforeListener: OnBeforeTransitionListener?
    ) {
        if (thumbnailView == null) {
            return
        }
        if (fullView is ImageView) {
            LogUtils.i("将大图的 image scale type 设置成 和小图一样的 ${thumbnailView.scaleType}")
            fullView.scaleType = thumbnailView.scaleType
        }
        beforeListener?.onBeforeTransitionLoad(itemView, fullView, thumbnailView)
        fullView.layoutParams = fullView.layoutParams.apply {
            width = thumbnailView.width
            height = thumbnailView.height
            AnimParentHelper.parentAnim(this, thumbnailView, fullView)
        }

    }


    override fun startTransition(
        fullView: View,
        thumbnailView: ImageView?
    ) {
        if (thumbnailView == null) {
            return
        }
        if (fullView is ImageView) {
            fullView.scaleType = ImageView.ScaleType.FIT_CENTER
        }
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
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                addTransition(ChangeImageTransform())
            }
            duration = durationTime
            interpolator = DecelerateInterpolator()
        }
    }

    override fun afterTransition(
        afterTransitionListener: OnAfterTransitionListener,
        holder: RecyclerView.ViewHolder
    ) {
        afterTransitionListener.onAfterTransitionLoad(true, holder)
    }


}