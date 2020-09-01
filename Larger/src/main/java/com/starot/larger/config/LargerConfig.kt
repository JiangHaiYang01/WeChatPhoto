package com.starot.larger.config

import com.starot.larger.impl.OnImageLoadListener
import com.starot.larger.impl.OnLoadProgressListener
import com.starot.larger.impl.OnVideoLoadListener


data class LargerConfig(
    //持续时间
    var duration: Long = 300,

    //是否自动加载大图
    var automaticLoadFullImage: Boolean = true,

    //图片加载器
    var imageLoad: OnImageLoadListener? = null,

    //视屏加载器
    var videoLoad: OnVideoLoadListener? = null,

    //加载的进度
    var imageProgress: OnLoadProgressListener? = null

)