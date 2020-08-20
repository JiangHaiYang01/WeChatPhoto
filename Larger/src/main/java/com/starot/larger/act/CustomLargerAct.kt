package com.starot.larger.act

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide

class CustomLargerAct : ListLargerAct<String>() {


    override fun onItemLoadThumbnails(
        itemView: View,
        position: Int,
        imageView: ImageView,
        data: String?
    ) {
        Glide.with(this)
            .load(data)
            .into(imageView)
    }

    override fun onItemLoadFull(
        itemView: View,
        position: Int,
        imageView: ImageView,
        data: String?
    ) {
    }

}