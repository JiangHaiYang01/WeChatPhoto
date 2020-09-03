package com.starot.larger.guest.impl

import android.view.MotionEvent

interface OnGuestTouchListener {
    fun onTouchEvent(ev: MotionEvent?): Boolean
}