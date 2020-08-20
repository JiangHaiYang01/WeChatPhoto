package com.starot.larger.impl

import android.view.View
import android.widget.ImageView

interface OnItemViewListener<T> {

    fun onItemLoadThumbnails(
        itemView: View,
        position: Int,
        imageView: ImageView,
        data: T?
    )

    fun onItemLoadFull(
        itemView: View,
        position: Int,
        imageView: ImageView,
        data: T?
    )

}