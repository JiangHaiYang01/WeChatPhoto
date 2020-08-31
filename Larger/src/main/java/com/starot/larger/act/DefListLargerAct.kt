package com.starot.larger.act

import android.annotation.SuppressLint
import android.view.View
import android.widget.ImageView
import com.starot.larger.bean.DefListData
import com.starot.larger.impl.OnCheckImageCacheListener
import com.starot.larger.impl.OnImageLoadListener
import com.starot.larger.utils.LogUtils

/***
 * 列表类型的 默认 示例
 *
 * 自定义的 可以参考下此种写法
 */
class DefListLargerAct : ListLargerAct<DefListData>() {


    //加载缩略图
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

    //加载大图
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

    //判断图片是否有缓存
    override fun getImageHasCache(
        itemView: View,
        position: Int,
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
                listConfig?.customItemViewListener?.itemImageHasCache(itemView, position, false)
            }

            override fun onHasCache() {
                listener.onHasCache()
                listConfig?.customItemViewListener?.itemImageHasCache(itemView, position, false)
            }
        })

    }

}