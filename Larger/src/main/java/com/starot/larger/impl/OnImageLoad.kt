package com.starot.larger.impl

import android.widget.ImageView

//加载图片接口
interface OnImageLoad : OnLoadProgressPrepareListener {


    //加载图片
    fun load(url: String, isLoadFull: Boolean, imageView: ImageView)

    //判断是有有缓存
    fun checkCache(url: String, listener: OnCheckImageCacheListener)

    //清理缓存
    fun clearCache()
}
