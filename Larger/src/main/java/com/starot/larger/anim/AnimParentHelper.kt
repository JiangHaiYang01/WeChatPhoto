package com.starot.larger.anim

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet

object AnimParentHelper {

    fun parentAnim(
        parent: ViewGroup.LayoutParams,
        thumbnailView: View,
        fullView: View
    ) {
        val location = AnimEnterHelper.getLocationOnScreen(thumbnailView)
        when (fullView.parent) {
            is ConstraintLayout -> {
                val constraintSet = ConstraintSet().apply {
                    clone(fullView.parent as ConstraintLayout)
                    clear(fullView.id, ConstraintSet.START)
                    clear(fullView.id, ConstraintSet.TOP)
                    clear(fullView.id, ConstraintSet.BOTTOM)
                    clear(fullView.id, ConstraintSet.RIGHT)
                    //重新建立约束
                    connect(
                        fullView.id, ConstraintSet.TOP, ConstraintSet.PARENT_ID,
                        ConstraintSet.TOP, location[1]
                    )
                    connect(
                        fullView.id, ConstraintSet.START, ConstraintSet.PARENT_ID,
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