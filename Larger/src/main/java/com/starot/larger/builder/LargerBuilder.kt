package com.starot.larger.builder

import android.content.Context
import android.content.Intent
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.starot.larger.Larger
import com.starot.larger.act.CustomLargerAct
import com.starot.larger.config.LargerConfig
import com.starot.larger.enums.Orientation
import com.starot.larger.impl.*
import com.starot.larger.view.progress.CircleProgressView
import com.starot.larger.view.progress.ImageProgressLoader

class LargerBuilder(private val listConfig: LargerConfig?) {

    //pos
    fun setIndex(pos: Int): LargerBuilder {
        listConfig?.position = pos
        return this
    }

    //设置持续时间
    fun setDuration(duration: Long): LargerBuilder {
        listConfig?.duration = duration
        return this
    }

    //是否自动加载
    fun setAutomatic(automatic: Boolean): LargerBuilder {
        listConfig?.automatic = automatic
        return this
    }

    //withSingle 模式下 设置imageView
    fun setImagesWithSingle(images: List<ImageView>): LargerBuilder {
        listConfig?.images = images
        return this
    }

    //设置数据源
    fun setData(data: List<OnLargerType>): LargerBuilder {
        listConfig?.data = data
        return this
    }


    //开始
    fun start(context: Context) {
        val intent = Intent(context, CustomLargerAct::class.java)
        context.startActivity(intent)
    }

    //跳转到自定义的act
    fun start(context: Context, cls: Class<*>) {
        val intent = Intent(context, cls)
        context.startActivity(intent)
    }


    //列表的情况需要将 recyclerview 也传入
    fun setRecyclerView(recyclerView: RecyclerView): LargerBuilder {
        listConfig?.recyclerView = recyclerView
        return this
    }


    //是否直接向上就能够拖动，微信直接向上不可以拖动，这里默认false
    fun setUpCanMove(canMove: Boolean): LargerBuilder {
        listConfig?.upCanMove = canMove
        return this
    }

    //是否自动加载下一页大图，默认不自动加载下一页
    fun setLoadNextFragment(auto:Boolean): LargerBuilder {
        listConfig?.loadNextFragment = auto
        return this
    }

    //是否打印日志
    fun setDebug(debug: Boolean): LargerBuilder {
        listConfig?.debug = debug
        return this
    }

    //设置背景颜色
    fun setBackgroundColor(color: Int): LargerBuilder {
        listConfig?.backgroundColor = color
        return this
    }

    //设置图片加载器
    fun setImageLoad(imageLoad: OnImageLoadListener): LargerBuilder {
        listConfig?.imageLoad = imageLoad
        return this
    }

    //设置加载的进度
    fun setProgressType(progressType: ImageProgressLoader.ProgressType): LargerBuilder {
        listConfig?.progressType = progressType
        return this
    }

    //设置是否显示加载框
    fun setProgressLoaderUse(use: Boolean): LargerBuilder {
        listConfig?.progressLoaderUse = use
        return this
    }

    //设置加载方向
    fun setOrientation(orientation: Orientation): LargerBuilder {
        listConfig?.orientation = orientation
        return this
    }

    //设置视屏加载器
    fun setVideoLoad(videoLoad: OnVideoLoadListener): LargerBuilder {
        listConfig?.videoLoad = videoLoad
        return this
    }

    //自定义布局
    fun setCustomListener(
        layoutId: Int,
        fullViewId: Int,
        listener: OnCustomImageLoadListener? = null
    ): LargerBuilder {
        listConfig?.layoutId = layoutId
        listConfig?.fullViewId = fullViewId
        listConfig?.customImageLoadListener = listener
        return this
    }

    fun setCustomListener(
        layoutId: Int,
        fullViewId: Int,
        progressId: Int,
        listener: OnCustomImageLoadListener? = null
    ): LargerBuilder {
        listConfig?.layoutId = layoutId
        listConfig?.progressId = progressId
        listConfig?.fullViewId = fullViewId
        listConfig?.customImageLoadListener = listener
        return this
    }

    //最大的比例
    fun setMaxScale(maxScale: Float): LargerBuilder {
        listConfig?.maxScale = maxScale
        return this
    }

    //设置双击中间的比例 可以和 max 相同 但是不能大于max
    fun setMediumScale(mediumScale: Float): LargerBuilder {
        listConfig?.mediumScale = mediumScale
        return this
    }


}