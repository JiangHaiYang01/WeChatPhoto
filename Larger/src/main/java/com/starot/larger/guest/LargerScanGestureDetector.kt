package com.starot.larger.guest

import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import com.starot.larger.guest.impl.OnGuestListener
import com.starot.larger.guest.impl.OnGuestTouchListener
import java.util.concurrent.atomic.AtomicBoolean


class LargerScanGestureDetector(
    view: View,
    private val guest: GuestAgent,
    val listener: OnGuestListener
) :
    ScaleGestureDetector.OnScaleGestureListener,
    OnGuestTouchListener {

    private var mIsScaleIng = AtomicBoolean(false)

    private var lastScale = 1.0f

    private var scaleGestureDetector = ScaleGestureDetector(view.context, this)

    override fun onScale(detector: ScaleGestureDetector?): Boolean {
        if (detector == null) {
            return false
        }
        if (!guest.isScaleIng()) return false
        val scaleFactor = detector.scaleFactor
        if (java.lang.Float.isNaN(scaleFactor) || java.lang.Float.isInfinite(scaleFactor)) return false
        if (scaleFactor >= 0) {

            //当前的伸缩值*之前的伸缩值 保持连续性
            val curScale = scaleFactor * lastScale
            if (listener.onScale(
                    curScale,
                    detector.focusX, detector.focusY
                )
            ) {
                lastScale = curScale
            }
        }
        return true
    }

    override fun onScaleBegin(detector: ScaleGestureDetector?): Boolean {
        if (detector == null) {
            return false
        }
        if (guest.isDragging()) {
            return false
        }
        mIsScaleIng.set(true)
        listener.onScaleStart()
        return true
    }

    override fun onScaleEnd(detector: ScaleGestureDetector?) {
        if (detector == null) {
            return
        }
        if (!guest.isScaleIng()) {
            return
        }
        if (mIsScaleIng.get()) {
            mIsScaleIng.set(false)
            listener.onScaleEnd(lastScale)
        }
    }

    //是否在缩放
    fun isScaleIng(): Boolean {
        return mIsScaleIng.get()
    }

    fun getScale(): Float {
        return lastScale
    }

    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        return scaleGestureDetector.onTouchEvent(ev)
    }
}