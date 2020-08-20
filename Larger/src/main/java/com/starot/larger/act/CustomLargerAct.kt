package com.starot.larger.act

import android.annotation.SuppressLint
import android.util.Log
import android.view.View
import android.widget.ImageView
import com.starot.larger.impl.OnImageLoad

class CustomLargerAct : ListLargerAct<String>() {


    override fun onItemLoadThumbnails(
        imageLoad: OnImageLoad?,
        itemView: View,
        position: Int,
        imageView: ImageView,
        data: String?
    ) {
        if (data != null)
            imageLoad?.load(data, false, imageView)
    }

    @SuppressLint("CheckResult")
    override fun onItemLoadFull(
        imageLoad: OnImageLoad?,
        itemView: View,
        position: Int,
        imageView: ImageView,
        data: String?
    ) {
        if (data != null)
            imageLoad?.load(data, true, imageView)
    }

}