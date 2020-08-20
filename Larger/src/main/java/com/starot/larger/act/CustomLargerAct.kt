package com.starot.larger.act

import android.annotation.SuppressLint
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions

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

    @SuppressLint("CheckResult")
    override fun onItemLoadFull(
        itemView: View,
        position: Int,
        imageView: ImageView,
        data: String?
    ) {
        val options = RequestOptions()
            .placeholder(imageView.drawable)
            .override(imageView.width, imageView.height)
            .skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
        Glide.with(this)
            .load(data)
            .apply(options)
            .into(imageView)
    }

}