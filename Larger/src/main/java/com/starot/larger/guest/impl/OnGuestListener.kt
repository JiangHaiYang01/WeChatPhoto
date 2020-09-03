package com.starot.larger.guest.impl

interface OnGuestListener : OnGuestTapListener, OnGuestDragListener {
    //缩放手势
    fun onScale(scaleFactor: Float, focusX: Float, focusY: Float)


}


interface OnGuestDragListener {
    //移动
    fun onDrag(x: Float, y: Float)

    fun onDragStart()

    fun onDragEnd()
}

interface OnGuestTapListener {
    //单点击手势
    fun onSingleTap()

    //双击手势
    fun onDoubleTap()
}