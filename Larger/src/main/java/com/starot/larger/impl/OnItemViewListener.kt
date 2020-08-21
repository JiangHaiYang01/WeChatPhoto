package com.starot.larger.impl

import android.view.View
import android.widget.ImageView

interface OnItemViewListener<T> {

    //加载缩略图
    fun onItemLoadThumbnails(
        imageLoad: OnImageLoad?,
        itemView: View,
        position: Int,
        imageView: ImageView,
        data: T?
    )

    //加载大图
    fun onItemLoadFull(
        imageLoad: OnImageLoad?,
        itemView: View,
        position: Int,
        imageView: ImageView,
        data: T?
    )

}