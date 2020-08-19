package com.starot.larger.act

import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
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

    override fun getThumbnailView(position: Int): ImageView? {
        val recyclerView = largerConfig?.recyclerView
//        Log.i(LargerAct.TAG, "childCount------> ${recyclerView?.childCount}")
//        Log.i(LargerAct.TAG, "position------> $position")
//        Log.i(LargerAct.TAG, "getItemId------> ${ recyclerView?.adapter?.itemCount}")
        val layoutManager = recyclerView?.layoutManager
//        Log.i(LargerAct.TAG, "is GridLayoutManager------> ${layoutManager is GridLayoutManager}")

        var pos = position
        when (layoutManager) {
            is GridLayoutManager -> {
                pos = getRecyclerViewId(layoutManager, pos)
            }
            is LinearLayoutManager -> {
                pos = getRecyclerViewId(layoutManager, pos)
            }
            is StaggeredGridLayoutManager -> {
//                pos = getRecyclerViewId(layoutManager, position, pos)
            }
        }
        val childAt = recyclerView?.getChildAt(pos) ?: return null
        return childAt as ImageView
    }
}

private fun getRecyclerViewId(
    layoutManager: LinearLayoutManager,
    pos: Int
): Int {
    var pos1 = pos
//        Log.i(
//            TAG,
//            "findFirstCompletelyVisibleItemPosition------> ${layoutManager.findFirstCompletelyVisibleItemPosition()}"
//        )
//        Log.i(
//            TAG,
//            "findFirstVisibleItemPosition------> ${layoutManager.findFirstVisibleItemPosition()}"
//        )
//        Log.i(
//            TAG,
//            "findLastCompletelyVisibleItemPosition------> ${layoutManager.findLastCompletelyVisibleItemPosition()}"
//        )
//        Log.i(
//            TAG,
//            "findLastVisibleItemPosition------> ${layoutManager.findLastVisibleItemPosition()}"
//        )
//
//
//        Log.i(TAG, "pos------> $position - ${layoutManager.findFirstVisibleItemPosition()}")
    pos1 -= layoutManager.findFirstVisibleItemPosition()
    return pos1
}

