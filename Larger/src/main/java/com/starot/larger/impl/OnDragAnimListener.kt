package com.starot.larger.impl

import com.starot.larger.adapter.ViewPagerAdapter
import com.starot.larger.view.image.PhotoView

interface OnDragAnimListener {

    fun onStartDrag(image: PhotoView, x: Float, y: Float)

    fun onEndDrag(
        image: PhotoView,
        holder: ViewPagerAdapter.PhotoViewHolder
    )
}