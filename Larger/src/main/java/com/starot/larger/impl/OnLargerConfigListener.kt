package com.starot.larger.impl

import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.starot.larger.Larger
import com.starot.larger.config.DefConfig
import com.starot.larger.enums.LargerEnum
import com.starot.larger.enums.Orientation
import com.starot.larger.view.progress.ImageProgressLoader

//处理返回一些 框架需要的数据
interface OnLargerConfigListener {


    //动画时长
    fun getDuration(): Long {
        return Larger.largerConfig?.duration ?: DefConfig.def_duration
    }

    //背景颜色
    fun getBackGroundColor(): Int {
        return Larger.largerConfig?.backgroundColor ?: DefConfig.def_back_color
    }

    //加载方向
    fun getOrientation(): Int {
        return if (Larger.largerConfig?.orientation == null) {
            ViewPager2.ORIENTATION_HORIZONTAL
        } else {
            if (Larger.largerConfig?.orientation == Orientation.ORIENTATION_HORIZONTAL) {
                ViewPager2.ORIENTATION_HORIZONTAL
            } else {
                ViewPager2.ORIENTATION_VERTICAL
            }
        }

    }


    //是否自动加载下一页大图数据
    fun isAutoLoadNextFragment(): Boolean {
        return Larger.largerConfig?.loadNextFragment ?: DefConfig.def_loadNextFragment
    }


    //缩放最大
    fun getMaxScale(): Float {
        val maxScale = Larger.largerConfig?.maxScale ?: return DefConfig.def_max_scale
        if (maxScale < DefConfig.def_max_scale_last_size) {
            return DefConfig.def_max_scale_last_size
        }
        return maxScale
    }

    //双击 中间的大小
    fun getMediumScale(): Float {
        val mediumScale = Larger.largerConfig?.mediumScale ?: return DefConfig.def_medium_scale
        if (getMaxScale() < mediumScale) {
            throw Throwable("mediumScale must <= maxScale")
        }
        return mediumScale
    }

    //缩放最小
    fun getMinScale(): Float {
        val minScale = Larger.largerConfig?.minScale ?: return DefConfig.def_min_scale
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
                getViewInSingles(position)
            }
            else -> {
                null
            }
        }
    }

    //是否自动加载
    fun isAutomatic(): Boolean {
        return Larger.largerConfig?.automatic ?: DefConfig.def_automatic
    }

    //获取singles 小图
    private fun getViewInSingles(position: Int): View? {
        val images = Larger.largerConfig?.images
        return images?.get(position)
    }

    //图片加载框的样式
    fun getProgressLoaderType(): ImageProgressLoader.ProgressType {
        return Larger.largerConfig?.progressType ?: DefConfig.def_progress_type
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