package com.starot.larger.impl

//判断图片是否有缓存
interface OnCheckImageCacheListener {
    //无缓存
    fun onNoCache()

    //有缓冲
    fun onHasCache()
}