package com.starot.larger.impl

import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.starot.larger.Larger
import com.starot.larger.enums.LargerEnum

//处理返回一些 框架需要的数据
interface OnLargerListener {

    //返回larger 界面的 需要加载的 大图 id
    fun getFullViewId(): Int

    //获取layout id
    fun getLayoutId(): Int


    //动画时长
    fun getDuration(): Long {
        return Larger.largerConfig?.duration ?: 300
    }


    //缩放最大
    fun getMaxScale(): Float {
        val maxScale = Larger.largerConfig?.maxScale ?: return 3.0f
        if (maxScale < 2.0f) {
            return 2.0f
        }
        return maxScale
    }

    //缩放最小
    fun getMinScale(): Float {
        val minScale = Larger.largerConfig?.minScale ?: return 0.2f
        if (minScale > 0.7f) {
            return 0.7f
        }
        return minScale
    }

    //获取小图
    fun getThumbnailView(position: Int): View? {
        return when (Larger.largerConfig?.largerType) {
            LargerEnum.LISTS -> {
                getViewInRecycler(position)
            }
            LargerEnum.SINGLES -> {
                getThumbnailView(position)
            }
            else -> {
                null
            }
        }
    }

    //获取singles 小图
    private fun getViewInSingles(position: Int): View? {
        val images = Larger.largerConfig?.images
        return images?.get(position)
    }


    private fun getViewInRecycler(position: Int): View? {
        val recyclerView = Larger.largerConfig?.recyclerView
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
        return recyclerView?.getChildAt(pos) ?: return null
    }


    //获取recyclerView Id
    private fun getRecyclerViewId(
        layoutManager: LinearLayoutManager,
        pos: Int
    ): Int {
        var pos1 = pos
        pos1 -= layoutManager.findFirstVisibleItemPosition()
        return pos1
    }

}