package com.starot.larger.config

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.starot.larger.enums.LargerEnum
import com.starot.larger.impl.OnImageLoadListener
import com.starot.larger.impl.OnLargerType


data class LargerConfig(

    //单个或者多个图片
    var images: List<View>? = null,

    //列表
    var recyclerView: RecyclerView? = null,

    //使用的类型
    var largerType: LargerEnum = LargerEnum.LISTS,

    //持续时间
    var duration: Long = 300,

    //数据集合
    var data: List<OnLargerType>? = null,

    //当前的下标
    var position: Int = 0,

    //列表的布局
    var layoutId: Int? = null,

    //大图的ImageViewID
    var fullViewId: Int? = null,

    //图片加载器
    var imageLoad: OnImageLoadListener? = null
)