package com.starot.larger.builder

import com.starot.larger.bean.DefListData
import com.starot.larger.config.BaseLargerConfig
import com.starot.larger.impl.OnCustomItemViewListener


interface BaseBuilder<T> {


    //设置列表的布局 如果不设置有默认的样式
    fun setItemLayout(layoutId: Int): T

    //大图的ImageViewID  不设置的话有默认
    fun setFullViewId(fullViewId: Int): T

    //自己处理viewHolder
    fun registerCustomItemView(listener: OnCustomItemViewListener): T


    //设置当前是第几个
    fun setCurrentIndex(pos: Int): T


    //设置默认的类型
    fun setDefData(data: List<DefListData>): T

    //设置数自定义据源 配合自定义的activity 使用
    fun setCustomData(data: List<Any>): T


}



