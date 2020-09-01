package com.starot.larger.impl

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView


interface OnBeforeTransitionListener {

    fun onBeforeTransitionLoad(fullView: View,
                               thumbnailView: ImageView?)
}