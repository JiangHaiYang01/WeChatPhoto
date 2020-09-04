package com.starot.larger.config

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.starot.larger.enums.LargerEnum
import com.starot.larger.impl.OnImageLoadListener
import com.starot.larger.impl.OnLargerType
import com.starot.larger.impl.OnVideoLoadListener


data class LargerConfig(

    //单个或者多个图片
    var images: List<View>? = null,

    //列表
    var recyclerView: RecyclerView? = null,

    //使用的类型
    var largerType: LargerEnum = LargerEnum.LISTS,

    //持续时间
    var duration: Long = 300,

    //最大缩放比例 （2 - f)
    var maxScale: Float = 3.0f,

    //最小缩放比例 (0.1f-0.7f)
    var minScale: Float = 0.2f,

    //数据集合
    var data: List<OnLargerType>? = null,

    //当前的下标
    var position: Int = 0,

    //列表的布局
    var layoutId: Int? = null,

    //大图的ImageViewID
    var fullViewId: Int? = null,

    //图片加载器
    var imageLoad: OnImageLoadListener? = null,

    //视屏加载器
    var videoLoad: OnVideoLoadListener? = null
)