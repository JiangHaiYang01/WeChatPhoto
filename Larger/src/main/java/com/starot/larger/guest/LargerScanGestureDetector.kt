package com.starot.larger.guest

import android.content.Context
import android.util.DisplayMetrics
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import android.view.WindowManager
import androidx.core.content.ContextCompat.getSystemService
import com.starot.larger.guest.impl.OnGuestListener
import com.starot.larger.guest.impl.OnGuestTouchListener


class LargerScanGestureDetector(view: View, val listener: OnGuestListener) :
    ScaleGestureDetector.OnScaleGestureListener,
    OnGuestTouchListener {

    private var scaleGestureDetector = ScaleGestureDetector(view.context, this)

    override fun onScale(detector: ScaleGestureDetector?): Boolean {
        if (detector == null) {
            return false
        }
        val scaleFactor = detector.scaleFactor
        if (java.lang.Float.isNaN(scaleFactor) || java.lang.Float.isInfinite(scaleFactor)) return false
        if (scaleFactor >= 0) {
            listener.onScale(
                scaleFactor,
                detector.focusX, detector.focusY
            )
        }
        return true
    }

    override fun onScaleBegin(detector: ScaleGestureDetector?): Boolean {
        if (detector == null) {
            return false
        }
        listener.onScaleStart()
        return true
    }

    override fun onScaleEnd(detector: ScaleGestureDetector?) {
        if (detector == null) {
            return
        }
        listener.onScaleEnd()
    }

    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        return scaleGestureDetector.onTouchEvent(ev)
    }
}