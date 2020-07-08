package com.starot.larger.anim

import android.animation.ValueAnimator
import android.graphics.Color
import android.view.View
import com.starot.larger.tools.ColorTool

object LargerDragAnim {
     fun startExitParentAnimDrag(
        start: Float,
        parent: View,
        end: Float,
        duration: Long
    ) {
        val valueAnimator = ValueAnimator()
        valueAnimator.duration = duration
        valueAnimator.setFloatValues(start, end)
        valueAnimator.addUpdateListener { animation ->
            parent.setBackgroundColor(
                ColorTool.getColorWithAlpha(Color.BLACK, (animation.animatedValue as Float))
            )
        }
        valueAnimator.start()
    }

     fun translationDrag(
        duration: Long,
        originalScale: Float,
        target: View
    ) {
        val valueAnimator = ValueAnimator()
        valueAnimator.duration = duration
        valueAnimator.setFloatValues(originalScale, 1f)
        valueAnimator.addUpdateListener { animation ->
            target.scaleX = animation.animatedValue as Float
            target.scaleY = animation.animatedValue as Float
        }
        valueAnimator.start()

        val translationX = target.translationX
        val translationY = target.translationY
        val translationXAnim = ValueAnimator()
        translationXAnim.duration = duration
        translationXAnim.setFloatValues(translationX, 0f)
        translationXAnim.addUpdateListener { animation ->
            target.translationX = animation.animatedValue as Float
        }
        translationXAnim.start()

        val translationYAnim = ValueAnimator()
        translationYAnim.duration = duration
        translationYAnim.setFloatValues(translationY, 0f)
        translationYAnim.addUpdateListener { animation ->
            target.translationY = animation.animatedValue as Float
        }
        translationYAnim.start()
    }

}