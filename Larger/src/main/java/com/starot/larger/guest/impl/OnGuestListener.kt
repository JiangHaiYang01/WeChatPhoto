package com.starot.larger.guest.impl

interface OnGuestListener : OnGuestTapListener {
    //缩放手势
    fun onScale(scaleFactor: Float, focusX: Float, focusY: Float)

    //移动
    fun onDrag(x: Float, y: Float)
}

interface OnGuestTapListener {
    //单点击手势
    fun onSingleTap()

    //双击手势
    fun onDoubleTap()
}