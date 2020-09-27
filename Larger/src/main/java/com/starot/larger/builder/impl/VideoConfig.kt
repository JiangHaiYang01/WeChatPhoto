package com.starot.larger.builder.impl

import com.starot.larger.config.LargerConfig
import com.starot.larger.impl.OnCustomImageLoadListener
import com.starot.larger.impl.OnImageLoadListener
import com.starot.larger.impl.OnVideoLoadListener
import com.starot.larger.view.progress.ImageProgressLoader


interface VideoConfig<T> : CommandConfig<T> {


    //设置图片加载器
    fun setImageLoad(imageLoad: OnImageLoadListener): T

    fun setVideoLoad(videoLoader: OnVideoLoadListener): T

}