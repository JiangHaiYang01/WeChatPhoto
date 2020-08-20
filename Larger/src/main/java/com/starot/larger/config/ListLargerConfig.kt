package com.starot.larger.config

import androidx.recyclerview.widget.RecyclerView

data class ListLargerConfig(
    //数据集合
    var data: List<Any>? = null,
    //当前的下标
    var position: Int = 0,
    //列表
    var recyclerView: RecyclerView? = null,
    //列表的布局
    var itemLayout: Int? = null,
    //大图的ImageViewID
    var fullViewId: Int? = null
)