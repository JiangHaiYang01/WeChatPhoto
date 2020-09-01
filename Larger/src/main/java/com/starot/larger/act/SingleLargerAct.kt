package com.starot.larger.act

import android.view.View
import android.widget.ImageView
import android.widget.VideoView
import com.starot.larger.Larger
import com.starot.larger.R
import com.starot.larger.config.SingleLargerConfig
import com.starot.larger.enums.FullType
import com.starot.larger.utils.LogUtils

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
        LogUtils.i("Larger.type ${Larger.type}")
        return singleConfig?.itemLayout ?: if (Larger.type == FullType.Image) {
            R.layout.item_larger_image
        } else {
            R.layout.item_larger_video
        }
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
        when (val view = itemView.findViewById<View>(getFullViewId())) {
            is ImageView -> {
                if (isLoadFull) {
                    onItemLoadFull(largerConfig?.imageLoad, itemView, position, view, data)
                } else {
                    onItemLoadThumbnails(largerConfig?.imageLoad, itemView, position, view, data)
                }
            }
            is VideoView -> {
                LogUtils.i("itemBindViewHolder is VideoView")
            }
        }

    }


    override fun getFullViewId(): Int {
        if (singleConfig?.itemLayout == null) {
            return R.id.image
        }
        return singleConfig?.fullViewId ?: R.id.image
    }

    override fun getVideoViewId(): Int {
        if (singleConfig?.itemLayout == null) {
            return R.id.videoView
        }
        return singleConfig?.videoViewId ?: R.id.videoView
    }

    override fun getThumbnailView(position: Int): ImageView? {
        val images = singleConfig?.images
        return images?.get(position)
    }


}

