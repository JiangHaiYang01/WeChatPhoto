package com.starot.larger.guest

import android.annotation.SuppressLint
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import com.starot.larger.guest.impl.OnGuestListener
import com.starot.larger.utils.LogUtils
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.math.sqrt

open class GuestAgent : View.OnTouchListener {

    private lateinit var largerGestureDetector: LargerGestureDetector
    private lateinit var largerScanGestureDetector: LargerScanGestureDetector
    private lateinit var listener: OnGuestListener

    private var mIsDragging = AtomicBoolean(false)

    private var mLastTouchX = 0f
    private var mActivePointerIndex = 0
    private var mLastTouchY = 0f
    private var mTouchSlop = 0f

    companion object {
        private const val INVALID_POINTER_ID = -1
    }

    private var mActivePointerId = INVALID_POINTER_ID


    @SuppressLint("ClickableViewAccessibility")
    fun setGuestView(view: View, listener: OnGuestListener) {
        this.listener = listener
        largerGestureDetector = LargerGestureDetector(view, listener)
        largerScanGestureDetector = LargerScanGestureDetector(view, listener)
        view.setOnTouchListener(this)
        initView(view)
    }

    private fun initView(view: View) {
        val configuration = ViewConfiguration.get(view.context)
        mTouchSlop = configuration.scaledTouchSlop.toFloat()
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {

        if (event == null) {
            return false
        }
        if (v == null) {
            return false
        }

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                v.parent?.requestDisallowInterceptTouchEvent(true)

                mActivePointerId = event.getPointerId(0)

                mLastTouchX = getActiveX(event)
                mLastTouchY = getActiveY(event)
                mIsDragging.set(false)
            }
            MotionEvent.ACTION_MOVE -> {
                val x = getActiveX(event)
                val y = getActiveY(event)
                val dx = x - mLastTouchX
                val dy = y - mLastTouchY

                if (!mIsDragging.get()) {
                    // Use Pythagoras to see if drag length is larger than
                    // touch slop
                    mIsDragging.set(sqrt(dx * dx + (dy * dy).toDouble()) >= mTouchSlop)
                    if (mIsDragging.get()) {
                        listener.onDragStart()
                    }
                }

            }
            MotionEvent.ACTION_CANCEL, MotionEvent.ACTION_UP -> {
                mActivePointerId = INVALID_POINTER_ID
                if (mIsDragging.get()) {
                    listener.onDragEnd()
                }
            }
            MotionEvent.ACTION_POINTER_UP -> {
                val pointerIndex: Int = getPointerIndex(event.action)
                val pointerId: Int = event.getPointerId(pointerIndex)
                if (pointerId == mActivePointerId) {
                    val newPointerIndex = if (pointerIndex == 0) 1 else 0
                    mActivePointerId = event.getPointerId(newPointerIndex)
                    mLastTouchX = event.getX(newPointerIndex)
                    mLastTouchY = event.getY(newPointerIndex)
                }
            }
        }

        mActivePointerIndex = event
            .findPointerIndex(if (mActivePointerId != INVALID_POINTER_ID) mActivePointerId else 0)



        return largerGestureDetector.onTouchEvent(event) or
                largerScanGestureDetector.onTouchEvent(event)
    }

    private fun getActiveX(ev: MotionEvent): Float {
        return try {
            ev.getX(mActivePointerIndex)
        } catch (e: Exception) {
            ev.x
        }
    }

    private fun getActiveY(ev: MotionEvent): Float {
        return try {
            ev.getY(mActivePointerIndex)
        } catch (e: Exception) {
            ev.y
        }
    }


    private fun getPointerIndex(action: Int): Int {
        return action and MotionEvent.ACTION_POINTER_INDEX_MASK shr MotionEvent.ACTION_POINTER_INDEX_SHIFT
    }
}