package com.starot.larger.builder.impl

import androidx.recyclerview.widget.RecyclerView
import com.starot.larger.config.LargerConfig
import com.starot.larger.impl.OnLargerType

interface MultiConfig<T> {
    //列表的情况需要将 recyclerview 也传入
    fun setRecyclerView(recyclerView: RecyclerView): T

    //pos
    fun setIndex(pos: Int): T

    //设置数据源
    fun setData(data: List<OnLargerType>): T

}