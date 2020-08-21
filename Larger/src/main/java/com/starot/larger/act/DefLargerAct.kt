package com.starot.larger.act

import android.annotation.SuppressLint
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.lifecycle.MutableLiveData
import com.starot.larger.bean.DefListData
import com.starot.larger.impl.OnCheckImageCacheListener
import com.starot.larger.impl.OnImageLoad

class DefLargerAct : ListLargerAct<DefListData>() {


    override fun onItemLoadThumbnails(
        imageLoad: OnImageLoad?,
        itemView: View,
        position: Int,
        imageView: ImageView,
        data: DefListData?
    ) {
        if (data != null)
            imageLoad?.load(data.thumbnails, false, imageView)
    }

    @SuppressLint("CheckResult")
    override fun onItemLoadFull(
        imageLoad: OnImageLoad?,
        itemView: View,
        position: Int,
        imageView: ImageView,
        data: DefListData?
    ) {
        if (data != null)
            imageLoad?.load(data.full, true, imageView)
    }

    override fun getImageHasCache(data: DefListData?, listener: OnCheckImageCacheListener) {
        if (data == null) {
            return
        }
        largerConfig?.imageLoad?.checkCache(data.full, listener)
    }

}