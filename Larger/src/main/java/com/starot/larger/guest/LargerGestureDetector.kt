package com.starot.larger.guest

import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import com.starot.larger.guest.impl.OnGuestListener
import com.starot.larger.guest.impl.OnGuestTouchListener
import com.starot.larger.utils.LogUtils

class LargerGestureDetector(imageView: View, private val listener: OnGuestListener) :
    GestureDetector.OnGestureListener,
    OnGuestTouchListener,
    GestureDetector.OnDoubleTapListener {

    private var gestureDetector = GestureDetector(imageView.context, this).apply {
        setOnDoubleTapListener(this@LargerGestureDetector)
    }


    override fun onDown(e: MotionEvent?): Boolean {
        return true
    }

    override fun onShowPress(e: MotionEvent?) {
    }

    override fun onSingleTapUp(e: MotionEvent?): Boolean {
        return true
    }

    //手势移动
    override fun onScroll(
        e1: MotionEvent?,//手指按下时的Event
        e2: MotionEvent?,//手指抬起时的Event
        distanceX: Float,//在 X 轴上划过的距离
        distanceY: Float//在 Y 轴上划过的距离
    ): Boolean {
        if (e2 != null && e1 != null) {
            listener.onDrag(
                e2.rawX - e1.rawX,
                e2.rawY - e1.rawY
            )
        }
        return true
    }

    override fun onLongPress(e: MotionEvent?) {
    }

    override fun onFling(
        e1: MotionEvent?,//手指按下时的Event
        e2: MotionEvent?,//手指抬起时的Event
        velocityX: Float,//在 X 轴上的运动速度(像素／秒)。
        velocityY: Float//在 Y 轴上的运动速度(像素／秒)。
    ): Boolean {

        return false
    }


    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        return gestureDetector.onTouchEvent(ev)
    }

    override fun onSingleTapConfirmed(e: MotionEvent?): Boolean {
        listener.onSingleTap()
        return true
    }

    override fun onDoubleTap(e: MotionEvent?): Boolean {
        listener.onDoubleTap()
        return true
    }

    override fun onDoubleTapEvent(e: MotionEvent?): Boolean {
        return true
    }
}