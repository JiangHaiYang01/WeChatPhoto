package com.starot.larger.act

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.starot.larger.Larger
import com.starot.larger.R
import com.starot.larger.config.SingleLargerConfig

abstract class SingleLargerAct<T> : LargerAct<T>() {


    var singleConfig: SingleLargerConfig? = null

    override fun beforeCreate() {
        singleConfig = Larger.singleConfig
    }


    override fun getIndex(): Int {
        return singleConfig?.position ?: 0
    }

    override fun getData(): List<T>? {
        return singleConfig?.data as List<T>?
    }

    override fun getItemLayout(): Int {
        return singleConfig?.itemLayout ?: R.layout.item_larger_image
    }

    override fun itemBindViewHolder(
        isLoadFull: Boolean,
        itemView: View,
        position: Int,
        data: T?
    ) {
        singleConfig?.customItemViewListener?.itemBindViewHolder(
            this,
            itemView,
            position,
            data
        )
        val imageView = itemView.findViewById<ImageView>(getFullViewId())
        if (isLoadFull) {
            onItemLoadFull(largerConfig?.imageLoad, itemView, position, imageView, data)
        } else {
            onItemLoadThumbnails(largerConfig?.imageLoad, itemView, position, imageView, data)
        }
    }

    override fun getDuration(): Long {
        return largerConfig?.duration ?: 300
    }

    override fun getFullViewId(): Int {
        if (singleConfig?.itemLayout == null) {
            return R.id.image
        }
        return singleConfig?.fullViewId ?: R.id.image
    }

    override fun getThumbnailView(position: Int): ImageView? {
        val images = singleConfig?.images
        return images?.get(position)
    }

    override fun getAutomaticLoadFullImage(): Boolean {
        return largerConfig?.automaticLoadFullImage ?: true
    }


}

