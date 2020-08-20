package com.starot.larger.config

import com.starot.larger.impl.OnImageLoad


data class LargerConfig(
    //持续时间
    var duration: Long = 300,

    //图片加载器
    var imageLoad: OnImageLoad? = null
)