package com.starot.larger.config

import android.graphics.Color
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.starot.larger.enums.LargerEnum
import com.starot.larger.impl.*


class DefConfig() {

    companion object {
        var def_duration = 300L
        var def_max_scale = 3.0f
        var def_max_scale_last_size = 1.0f
        var def_medium_scale = 1.5f
        var def_min_scale = 0.2f
        var def_back_color = Color.BLACK
        var def_automatic = true
    }
}


data class LargerConfig(

    //单个或者多个图片
    var images: List<View>? = null,

    //列表
    var recyclerView: RecyclerView? = null,

    //使用的类型
    var largerType: LargerEnum = LargerEnum.LISTS,

    //持续时间
    var duration: Long = DefConfig.def_duration,

    //是否自动加载大图
    var automatic: Boolean = DefConfig.def_automatic,

    //最大缩放比例 （2 - f)
    var maxScale: Float = DefConfig.def_max_scale,

    //双击中间的大小 需要小于等于 max
    var mediumScale: Float = DefConfig.def_medium_scale,

    //最小缩放比例 (0.1f-0.7f)
    var minScale: Float = DefConfig.def_min_scale,

    //数据集合
    var data: List<OnLargerType>? = null,

    //当前的下标
    var position: Int = 0,

    //列表的布局
    var layoutId: Int? = null,

    //大图的ImageViewID
    var fullViewId: Int? = null,

    //默认的背景颜色
    var backgroundColor: Int = DefConfig.def_back_color,

    //自定义
    var customImageLoadListener: OnCustomImageLoadListener? = null,

    //图片加载器
    var imageLoad: OnImageLoadListener? = null,

    //加载的进度
    var progressLoad: OnLoadProgressListener? = null,

    //视屏加载器
    var videoLoad: OnVideoLoadListener? = null
)