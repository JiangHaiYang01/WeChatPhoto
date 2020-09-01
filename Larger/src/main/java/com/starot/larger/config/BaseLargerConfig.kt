package com.starot.larger.config

import com.starot.larger.impl.OnCustomItemViewListener

open class BaseLargerConfig(
    //数据集合
    var data: List<Any>? = null,
    //当前的下标
    var position: Int = 0,
    //列表的布局
    var itemLayout: Int? = null,
    //大图的ImageViewID
    var fullViewId: Int? = null,
    //VideoView id
    var videoViewId: Int? = null,
    //自定义的itemViewListener
    var customItemViewListener: OnCustomItemViewListener? = null
)