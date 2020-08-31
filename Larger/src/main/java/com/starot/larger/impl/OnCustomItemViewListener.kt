package com.starot.larger.impl

import android.view.View


interface OnCustomItemViewListener {

    //自定义的 itemViewHolder 让用户自行处理
    fun itemBindViewHolder(
        listener: OnReLoadFullImage,
        itemView: View,
        position: Int,
        data: Any?
    )

    //是否有缓存
    fun itemImageHasCache(itemView: View, position: Int, hasCache: Boolean)

    //大图加载完成的回调
    fun itemImageFullLoad(itemView: View, position: Int)
}