package com.starot.larger.builder.impl

import com.starot.larger.config.LargerConfig
import com.starot.larger.impl.OnCustomImageLoadListener
import com.starot.larger.impl.OnImageLoadListener
import com.starot.larger.view.progress.ImageProgressLoader


interface ImageConfig<T> : CommandConfig<T> {
    //是否自动加载
    fun setAutomatic(automatic: Boolean): T

    //最大的比例
    fun setMaxScale(maxScale: Float): T

    //设置双击中间的比例 可以和 max 相同 但是不能大于max
    fun setMediumScale(mediumScale: Float): T

    //设置图片加载器
    fun setImageLoad(imageLoad: OnImageLoadListener): T

    //设置加载的进度
    fun setProgressType(progressType: ImageProgressLoader.ProgressType): T

    //设置是否显示加载框
    fun setProgressLoaderUse(use: Boolean): T

    //自定义布局
    fun setCustomListener(
        layoutId: Int,
        fullViewId: Int,
        listener: OnCustomImageLoadListener? = null
    ): T

    //自定义布局
    fun setCustomListener(
        layoutId: Int,
        fullViewId: Int,
        progressId: Int,
        listener: OnCustomImageLoadListener? = null
    ): T
}