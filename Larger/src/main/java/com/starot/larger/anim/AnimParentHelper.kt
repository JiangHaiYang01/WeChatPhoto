package com.starot.larger.anim

import android.view.ViewGroup
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet

object AnimParentHelper {

     fun parentAnim(
         parent: ViewGroup.LayoutParams,
         thumbnailView: ImageView,
         fullView: ImageView,
         photoId: Int
     ) {
        val location = AnimEnterHelper.getLocationOnScreen(thumbnailView)
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
                if (parent is ViewGroup.MarginLayoutParams) {
                    parent.marginStart = location[0]
                    parent.topMargin = location[1]
                }
            }
        }
    }
}