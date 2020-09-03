package com.starot.larger

import com.starot.larger.builder.Builder
import com.starot.larger.config.LargerConfig
import com.starot.larger.enums.LargerEnum

object Larger {

    var largerConfig: LargerConfig? = null

    fun create(): Builder {
        return Builder()
    }

    class Builder() {

        //列表类型
        fun withListType(): com.starot.larger.builder.Builder {
            largerConfig = LargerConfig()
            return Builder(largerConfig?.apply {
                largerType = LargerEnum.LISTS
            })
        }

    }
}