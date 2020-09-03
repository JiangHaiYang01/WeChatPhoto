package com.starot.larger.guest.impl

interface OnGuestListener : OnGuestTapListener {

}

interface OnGuestTapListener {
    //单点击手势
    fun onSingleTap()

    //双击手势
    fun onDoubleTap()
}