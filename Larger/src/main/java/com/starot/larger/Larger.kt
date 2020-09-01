package com.starot.larger

import com.starot.larger.builder.ListBuilder
import com.starot.larger.builder.SingleBuilder
import com.starot.larger.config.LargerConfig
import com.starot.larger.config.ListLargerConfig
import com.starot.larger.config.SingleLargerConfig
import com.starot.larger.enums.FullType
import com.starot.larger.impl.OnImageLoadListener
import com.starot.larger.impl.OnLoadProgressListener
import com.starot.larger.impl.OnVideoLoadListener
import com.starot.larger.utils.LogUtils

object Larger {


    fun create(): Builder {
        return Builder(LargerConfig())
    }

    fun clear(){
        config = null
        listConfig = null
        singleConfig = null
        type = FullType.Image
    }

    var config: LargerConfig? = null
    var listConfig: ListLargerConfig? = null
    var singleConfig: SingleLargerConfig? = null
    var type = FullType.Image


    class Builder(private val largerConfig: LargerConfig) {

        init {
            config = largerConfig
        }

        //设置持续时间
        fun setDuration(duration: Long): Builder {
            largerConfig.duration = duration
            return this
        }

        //设置图片加载器
        fun setImageLoad(imageLoad: OnImageLoadListener): Builder {
            largerConfig.imageLoad = imageLoad
            return this
        }

        //设置视屏加载器
        fun setVideoLoad(videoLoad: OnVideoLoadListener): Builder {
            largerConfig.videoLoad = videoLoad
            return this
        }

        //设置加载的进度
        fun setProgress(imageProgress: OnLoadProgressListener): Builder {
            largerConfig.imageProgress = imageProgress
            return this
        }

        //是否自动加载大图 在 audio 模式下无效
        fun setAutomaticLoadFullImage(automatic: Boolean): Builder {
            largerConfig.automaticLoadFullImage = automatic
            return this
        }

        //兼容视屏模式
        fun asAudio(): Builder {
            type = FullType.Audio
            return this
        }

        //列表类型
        fun withListType(): ListBuilder {
            listConfig = ListLargerConfig()
            return ListBuilder(listConfig)
        }

        //单独的view
        fun withSingleType(): SingleBuilder {
            singleConfig = SingleLargerConfig()
            return SingleBuilder(singleConfig)
        }
    }
}