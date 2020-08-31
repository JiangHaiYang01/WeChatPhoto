package com.starot.larger.builder

import com.starot.larger.bean.DefListData
import com.starot.larger.config.BaseLargerConfig
import com.starot.larger.impl.OnCustomItemViewListener


interface BaseBuilder<T> {


    /**
     * link [setCustomImageListener] 3参数的方法
     */
    fun setCustomImageListener(
        layoutId: Int,
        fullViewId: Int
    ): T

    /**
     * [layoutId]   设置列表的布局
     * [fullViewId] 大图的ImageViewID
     * [listener]   自己处理viewHolder 回调
     * 设置自定义图片的类型 不使用此接口则拥有默认设置
     * 默认的 layoutId   R.layout.item_larger_image
     * 默认的 fullViewId R.id.image
     *
     */
    fun setCustomImageListener(
        layoutId: Int,
        fullViewId: Int,
        listener: OnCustomItemViewListener?
    ): T

    //设置当前是第几个
    fun setCurrentIndex(pos: Int): T


    //设置默认的类型
    fun setDefData(data: List<DefListData>): T

    //设置数自定义据源 配合自定义的activity 使用
    fun setCustomData(data: List<Any>): T


}



