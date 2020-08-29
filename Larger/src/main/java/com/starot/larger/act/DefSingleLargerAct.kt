package com.starot.larger.act

import android.annotation.SuppressLint
import android.view.View
import android.widget.ImageView
import com.starot.larger.bean.DefListData
import com.starot.larger.impl.OnCheckImageCacheListener
import com.starot.larger.impl.OnImageLoadListener
import com.starot.larger.utils.LogUtils

class DefSingleLargerAct : SingleLargerAct<DefListData>() {


    override fun onItemLoadThumbnails(
        imageLoad: OnImageLoadListener?,
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
        imageLoad: OnImageLoadListener?,
        itemView: View,
        position: Int,
        imageView: ImageView,
        data: DefListData?
    ) {
        if (data != null)
            imageLoad?.load(data.full, true, imageView)
    }

    override fun getImageHasCache(
        itemView: View,
        data: DefListData?,
        listener: OnCheckImageCacheListener
    ) {
        LogUtils.i("def larger act 判断是否图片有缓存")
        if (data == null) {
            LogUtils.i("def larger act 判断是否图片有缓存 data is null")
            return
        }
        LogUtils.i("def larger act 判断是否图片有缓存 data is ${data.full}")
        largerConfig?.imageLoad?.checkCache(data.full, object : OnCheckImageCacheListener {
            override fun onNoCache() {
                listener.onNoCache()
                singleConfig?.customItemViewListener?.itemImageHasCache(itemView, false)
            }

            override fun onHasCache() {
                listener.onHasCache()
                singleConfig?.customItemViewListener?.itemImageHasCache(itemView, true)
            }
        })

    }

}