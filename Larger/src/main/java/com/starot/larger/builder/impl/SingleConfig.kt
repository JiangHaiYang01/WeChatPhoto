package com.starot.larger.builder.impl

import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.starot.larger.builder.config.ImageMultiConfig
import com.starot.larger.impl.OnLargerType

interface SingleConfig<T> {

    //withSingle 模式下 设置imageView
    fun setImage(image: ImageView): T

    //设置数据源
    fun setData(data: OnLargerType): T
}

