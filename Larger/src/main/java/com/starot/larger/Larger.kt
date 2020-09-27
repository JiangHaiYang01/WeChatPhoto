package com.starot.larger

import com.starot.larger.builder.LargerBuilder
import com.starot.larger.builder.config.ImageMultiConfig
import com.starot.larger.builder.config.ImageSingleConfig
import com.starot.larger.builder.config.VideoMultiConfig
import com.starot.larger.builder.config.VideoSingleConfig
import com.starot.larger.config.LargerConfig
import com.starot.larger.enums.LargerEnum

object Larger {

    var largerConfig: LargerConfig? = null

    fun create(): Builder {
        return Builder()
    }

    class Builder() {


        fun withImageMulti(): ImageMultiConfig {
            largerConfig = LargerConfig()
            return ImageMultiConfig(largerConfig)
        }

        fun withImageSingle(): ImageSingleConfig {
            largerConfig = LargerConfig()
            return ImageSingleConfig(largerConfig)
        }


        fun withVideoMulti(): VideoMultiConfig {
            largerConfig = LargerConfig()
            return VideoMultiConfig(largerConfig)
        }

        fun withVideoSingle(): VideoSingleConfig {
            largerConfig = LargerConfig()
            return VideoSingleConfig(largerConfig)
        }


        //列表类型
        fun withListType(): LargerBuilder {
            largerConfig = LargerConfig()
            return LargerBuilder(largerConfig?.apply {
                largerType = LargerEnum.LISTS
            })
        }


    }
}