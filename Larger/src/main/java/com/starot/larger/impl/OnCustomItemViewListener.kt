package com.starot.larger.impl

import android.view.View


interface OnCustomItemViewListener {

    //自定义的 itemViewHolder 让用户自行处理
    fun itemBindViewHolder(
        listener:OnReLoadFullImage,
        itemView: View,
        position: Int,
        data: Any?
    )
}