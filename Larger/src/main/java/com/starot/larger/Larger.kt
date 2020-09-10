package com.starot.larger

import com.starot.larger.builder.LargerBuilder
import com.starot.larger.config.LargerConfig
import com.starot.larger.enums.LargerEnum

object Larger {

    var largerConfig: LargerConfig? = null

    fun create(): Builder {
        return Builder()
    }

    class Builder() {

        //列表类型
        fun withListType(): LargerBuilder {
            largerConfig = LargerConfig()
            return LargerBuilder(largerConfig?.apply {
                largerType = LargerEnum.LISTS
            })
        }

        fun withSingle(): LargerBuilder {
            largerConfig = LargerConfig()
            return LargerBuilder(largerConfig?.apply {
                largerType = LargerEnum.SINGLES
            })
        }

    }
}