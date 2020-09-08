package com.starot.larger.image

interface OnLargerDragListener {


    fun onDrag(x: Float, y: Float){}

    fun onDragEnd(){}

    fun onDragPrepare():Boolean{return true}

    fun onDragStart(){}
}