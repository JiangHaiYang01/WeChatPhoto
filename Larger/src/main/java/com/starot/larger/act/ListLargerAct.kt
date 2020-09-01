package com.starot.larger.act

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.starot.larger.Larger
import com.starot.larger.R
import com.starot.larger.config.ListLargerConfig

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
        return listConfig?.itemLayout ?: R.layout.item_larger_image
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
        if (listConfig?.itemLayout == null) {
            return R.id.image
        }
        return listConfig?.fullViewId ?: R.id.image
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

    override fun getAutomaticLoadFullImage(): Boolean {
        return largerConfig?.automaticLoadFullImage ?: true
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

