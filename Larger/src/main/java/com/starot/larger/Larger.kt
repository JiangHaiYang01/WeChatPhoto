package com.starot.larger

import com.starot.larger.builder.ListBuilder
import com.starot.larger.config.LargerConfig
import com.starot.larger.config.ListLargerConfig

object Larger {


    fun create(): Builder {
        return Builder(LargerConfig())
    }


    var config: LargerConfig? = null
    var listConfig: ListLargerConfig? = null


    class Builder(private val largerConfig: LargerConfig) {


        //设置持续时间
        fun setDuration(duration: Long): Builder {
            largerConfig.duration = duration
            return this
        }

        //列表类型
        fun  setListType(): ListBuilder {
            listConfig = ListLargerConfig()
            return ListBuilder(listConfig)
        }
    }
}