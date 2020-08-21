package com.starot.larger.impl

import android.widget.ImageView

interface OnImageLoad :  OnLoadProgressPrepareListener {


    fun load(url: String, isLoadFull: Boolean, imageView: ImageView)
}
