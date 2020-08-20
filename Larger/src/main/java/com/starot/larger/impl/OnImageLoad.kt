package com.starot.larger.impl

import android.widget.ImageView

interface OnImageLoad : OnLoadProgress, OnProgressStatusChangeListener {


    fun load(url: String, isLoadFull: Boolean, imageView: ImageView)
}
