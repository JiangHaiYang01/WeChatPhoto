package com.starot.larger

import com.starot.larger.builder.ListBuilder
import com.starot.larger.config.LargerConfig
import com.starot.larger.config.ListLargerConfig
import com.starot.larger.impl.OnImageLoad
import com.starot.larger.impl.OnLoadProgressListener

object Larger {


    fun create(): Builder {
        return Builder(LargerConfig())
    }


    var config: LargerConfig? = null
    var listConfig: ListLargerConfig? = null


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
        fun setImageLoad(imageLoad: OnImageLoad): Builder {
            largerConfig.imageLoad = imageLoad
            return this
        }

        //设置加载的进度
        fun setProgress(imageProgress: OnLoadProgressListener): Builder {
            largerConfig.imageProgress = imageProgress
            return this
        }

        //列表类型
        fun withListType(): ListBuilder {
            listConfig = ListLargerConfig()
            return ListBuilder(listConfig)
        }
    }
}