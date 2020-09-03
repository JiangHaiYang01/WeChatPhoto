package com.starot.larger.guest

import android.view.View
import com.starot.larger.guest.impl.OnGuestListener

open class GuestAgent {

    fun setGuestView(view: View, listener: OnGuestListener) {
        val largerGestureDetector = LargerGestureDetector(view, listener)
        view.setOnTouchListener { _, event -> largerGestureDetector.onTouchEvent(event) }
    }
}