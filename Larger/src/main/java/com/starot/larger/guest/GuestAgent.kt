package com.starot.larger.guest

import android.annotation.SuppressLint
import android.view.View
import com.starot.larger.guest.impl.OnGuestListener

open class GuestAgent {

    @SuppressLint("ClickableViewAccessibility")
    fun setGuestView(view: View, listener: OnGuestListener) {
        val largerGestureDetector = LargerGestureDetector(view, listener)
        val largerScanGestureDetector = LargerScanGestureDetector(view, listener)
        view.setOnTouchListener { _, event ->
            largerGestureDetector.onTouchEvent(event) or
                    largerScanGestureDetector.onTouchEvent(event)
        }
    }
}