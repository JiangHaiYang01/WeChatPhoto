package com.starot.larger.act

import android.annotation.SuppressLint
import android.view.View
import android.widget.ImageView
import android.widget.VideoView
import com.starot.larger.Larger
import com.starot.larger.bean.DefListData
import com.starot.larger.enums.FullType
import com.starot.larger.impl.OnCheckImageCacheListener
import com.starot.larger.impl.OnImageLoadListener
import com.starot.larger.impl.OnVideoLoadListener
import com.starot.larger.utils.LogUtils

/***
 * 单个或者非列表类型的 默认 示例
 *
 * 自定义的 可以参考下此种写法
 */
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
        position: Int,
        data: DefListData?,
        listener: OnCheckImageCacheListener
    ) {
        if (data == null) {
            LogUtils.i("def single  larger act 判断是否图片有缓存 data is null")
            return
        }
        LogUtils.i("def single  larger act 判断是否图片有缓存 data is ${data.full}")
        largerConfig?.imageLoad?.checkCache(data.full, object : OnCheckImageCacheListener {
            override fun onNoCache() {
                listener.onNoCache()
                singleConfig?.customItemViewListener?.itemImageHasCache(itemView, position, false)
            }

            override fun onHasCache() {
                listener.onHasCache()
                singleConfig?.customItemViewListener?.itemImageHasCache(itemView, position, false)
            }
        })
    }

    override fun onItemLoadAudio(
        videoLoad: OnVideoLoadListener?,
        itemView: View,
        position: Int,
        data: DefListData?
    ) {
        LogUtils.i("def single larger act 加载 video")
        if (data != null) {
            videoLoad?.load(data.full, view = itemView)
        }
    }


}