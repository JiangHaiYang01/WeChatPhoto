package com.starot.larger.config

import android.graphics.Color
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.starot.larger.enums.LargerEnum
import com.starot.larger.enums.Orientation
import com.starot.larger.impl.*
import com.starot.larger.view.progress.ImageProgressLoader


class DefConfig() {

    companion object {
        var def_duration = 300L
        var def_max_scale = 3.0f
        var def_max_scale_last_size = 1.0f
        var def_medium_scale = 1.5f
        var def_min_scale = 0.2f
        var def_back_color = Color.BLACK
        var def_automatic = true
        var def_debug = false
        var def_progress_type = ImageProgressLoader.ProgressType.FULL
        var def_up_can_move = false
        var def_loadNextFragment = false
        var def_progress_use = true
        var orientation = Orientation.ORIENTATION_HORIZONTAL
    }
}


data class LargerConfig(

    //是否直接向上就能够拖动，微信直接向上不可以拖动，这里默认false
    var upCanMove: Boolean = DefConfig.def_up_can_move,

    //是否自动加载下一页，默认不自动加载下一页
    var loadNextFragment :Boolean = DefConfig.def_loadNextFragment,

    //是否打印日志
    var debug: Boolean = DefConfig.def_debug,

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

    //获取加载框的id
    var progressId: Int? = null,

    //默认的背景颜色
    var backgroundColor: Int = DefConfig.def_back_color,

    //自定义
    var customImageLoadListener: OnCustomImageLoadListener? = null,

    //图片加载器
    var imageLoad: OnImageLoadListener? = null,

    //加载y样式
    var progressType: ImageProgressLoader.ProgressType = DefConfig.def_progress_type,

    //是否使用加载框
    var progressLoaderUse: Boolean = DefConfig.def_progress_use,

    //设置滑动方向
    var orientation: Orientation = DefConfig.orientation,

    //视屏加载器
    var videoLoad: OnVideoLoadListener? = null
)