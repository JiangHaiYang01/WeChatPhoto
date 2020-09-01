package com.starot.larger.act

import android.view.View
import android.widget.ImageView
import android.widget.VideoView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.starot.larger.Larger
import com.starot.larger.R
import com.starot.larger.config.ListLargerConfig
import com.starot.larger.enums.FullType
import com.starot.larger.utils.LogUtils

abstract class ListLargerAct<T> : LargerAct<T>() {


    var listConfig: ListLargerConfig? = null

    override fun beforeCreate() {
        listConfig = Larger.listConfig
    }


    override fun getIndex(): Int {
        return listConfig?.position ?: 0
    }

    override fun getData(): List<T>? {
        return listConfig?.data as List<T>?
    }

    override fun getItemLayout(): Int {
        return listConfig?.itemLayout ?: if (Larger.type == FullType.Image) {
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
        listConfig?.customItemViewListener?.itemBindViewHolder(
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
        if (listConfig?.itemLayout == null) {
            return R.id.image
        }
        return listConfig?.fullViewId ?: R.id.image
    }

    override fun getVideoViewId(): Int {
        if (listConfig?.itemLayout == null) {
            return R.id.videoView
        }
        return listConfig?.videoViewId ?: R.id.videoView
    }

    override fun getThumbnailView(position: Int): ImageView? {
        val recyclerView = listConfig?.recyclerView
        val layoutManager = recyclerView?.layoutManager

        var pos = position
        when (layoutManager) {
            is GridLayoutManager -> {
                pos = getRecyclerViewId(layoutManager, pos)
            }
            is LinearLayoutManager -> {
                pos = getRecyclerViewId(layoutManager, pos)
            }
            is StaggeredGridLayoutManager -> {
            }
        }
        val childAt = recyclerView?.getChildAt(pos) ?: return null
        return childAt as ImageView
    }



    private fun getRecyclerViewId(
        layoutManager: LinearLayoutManager,
        pos: Int
    ): Int {
        var pos1 = pos
        pos1 -= layoutManager.findFirstVisibleItemPosition()
        return pos1
    }
}

