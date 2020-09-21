package com.example.largerloadvideo

import android.content.Context
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.ViewConfiguration
import com.starot.larger.view.image.OnLargerDragListener
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.math.sqrt


class LargerDrag(private val listener: OnLargerDragListener, context: Context) :
    GestureDetector.SimpleOnGestureListener() {

     val isDragging = AtomicBoolean(false)

    private val mTouchSlop = ViewConfiguration.get(context).scaledEdgeSlop
    override fun onScroll(
        e1: MotionEvent?,
        e2: MotionEvent?,
        distanceX: Float,
        distanceY: Float
    ): Boolean {
        if (e1 != null && e2 != null) {

            if (!isDragging.get()) {
                val dx = e2.rawX - e1.rawX
                val dy = e2.rawY - e1.rawY
                if (listener.onDragPrepare(dx, dy)) {
                    isDragging.set(sqrt(dx * dx + (dy * dy).toDouble()) >= mTouchSlop)
                    if (isDragging.get()) {
                        listener.onDragStart()
                    }
                }
            } else {
                listener.onDrag(
                    e2.rawX - e1.rawX,
                    e2.rawY - e1.rawY
                )
            }
        }
        return super.onScroll(e1, e2, distanceX, distanceY)
    }

}