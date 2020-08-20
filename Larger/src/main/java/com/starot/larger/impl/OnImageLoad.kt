package com.starot.larger.impl

import android.widget.ImageView

interface OnImageLoad : OnLoadProgress {


    fun load(url: String, isLoadFull: Boolean, imageView: ImageView)
}
