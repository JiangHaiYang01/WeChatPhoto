package com.starot.larger.impl

import android.view.View

//自定义的 itemViewHolder 让用户自行处理
interface OnCustomItemViewListener {

    fun itemBindViewHolder(
        listener:OnReLoadFullImage,
        itemView: View,
        position: Int,
        data: Any?
    )
}