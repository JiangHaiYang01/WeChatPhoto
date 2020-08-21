package com.starot.larger.impl

import android.view.View

interface OnCustomItemViewListener {

    fun itemBindViewHolder(
        listener:OnReLoadFullImage,
        itemView: View,
        position: Int,
        data: Any?
    )
}