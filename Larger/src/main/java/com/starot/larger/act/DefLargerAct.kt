package com.starot.larger.act

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.starot.larger.Larger
import com.starot.larger.R

class DefLargerAct : LargerAct<String>() {


    private var largerConfig: Larger.LargerConfig? = null

    override fun beforeCreate() {
        largerConfig = Larger.config
    }


    override fun getIndex(): Int {
        return largerConfig?.position ?: 0
    }

    override fun getData(): List<String>? {
        return largerConfig?.thumbnails
    }

    override fun getItemLayout(): Int {
        return R.layout.item_larger_image
    }

    override fun itemBindViewHolder(
        isLoadFull: Boolean,
        itemView: View,
        position: Int,
        data: String?
    ) {

        //todo 需要处理大图和小图 选用不同的加载器 这里需要用到策略

        Glide.with(this)
            .load(largerConfig?.thumbnails?.get(position))
            .into(itemView.findViewById(R.id.image))


    }

    override fun getDuration(): Long {
        return largerConfig?.duration ?: 300
    }

    override fun getFullViewId(): Int {
        return R.id.image
    }

    override fun getThumbnailView(position: Int): ImageView {
        return largerConfig?.recyclerView?.getChildAt(position) as ImageView
    }


}