package com.starot.larger.guest.impl

interface OnGuestListener : OnGuestTapListener, OnGuestDragListener, OnGuestScaleListener {

    fun onTranslate(dx: Float, dy: Float)

}


interface OnGuestScaleListener {
    //缩放结束
    fun onScaleStart()

    //缩放手势
    fun onScale(scale: Float, focusX: Float, focusY: Float):Boolean

    //缩放结束
    fun onScaleEnd(scale: Float)
}


interface OnGuestDragListener {
    //移动
    fun onDrag(x: Float, y: Float)

    //开始移动
    fun onDragStart()

    //结束移动
    fun onDragEnd()
}

interface OnGuestTapListener {
    //单点击手势
    fun onSingleTap()

    //长按
    fun onLongPress()

    //双击手势
    fun onDoubleTap()
}