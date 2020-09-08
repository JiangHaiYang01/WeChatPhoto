package com.starot.larger.image

interface OnLargerDragListener {


    fun onDrag(x: Float, y: Float) {}

    fun onDragEnd() {}

    fun onDragStart() {}

    fun onDragPrepare(dx: Float, dy: Float): Boolean {
        return true
    }
}