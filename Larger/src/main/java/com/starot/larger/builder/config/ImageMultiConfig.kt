package com.starot.larger.builder.config

import android.content.Context
import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import com.starot.larger.act.CustomLargerAct
import com.starot.larger.act.LargerAct
import com.starot.larger.builder.impl.ImageConfig
import com.starot.larger.builder.impl.MultiConfig
import com.starot.larger.config.LargerConfig
import com.starot.larger.enums.LargerEnum
import com.starot.larger.enums.Orientation
import com.starot.larger.impl.OnCustomImageLoadListener
import com.starot.larger.impl.OnImageLoadListener
import com.starot.larger.impl.OnLargerType
import com.starot.larger.view.progress.ImageProgressLoader

class ImageMultiConfig(private val largerConfig: LargerConfig?) :
    MultiConfig<ImageMultiConfig>, ImageConfig<ImageMultiConfig> {

    init {
        largerConfig?.largerType = LargerEnum.LISTS
    }

    override fun setRecyclerView(recyclerView: RecyclerView): ImageMultiConfig {
        largerConfig?.recyclerView = recyclerView
        return this
    }

    override fun setData(data: List<OnLargerType>): ImageMultiConfig {
        largerConfig?.data = data
        return this
    }

    override fun setAutomatic(automatic: Boolean): ImageMultiConfig {
        largerConfig?.automatic = automatic
        return this
    }

    override fun setMaxScale(maxScale: Float): ImageMultiConfig {
        largerConfig?.maxScale = maxScale
        return this
    }

    override fun setMediumScale(mediumScale: Float): ImageMultiConfig {
        largerConfig?.mediumScale = mediumScale
        return this
    }

    override fun setImageLoad(imageLoad: OnImageLoadListener): ImageMultiConfig {
        largerConfig?.imageLoad = imageLoad
        return this
    }

    override fun setProgressType(progressType: ImageProgressLoader.ProgressType): ImageMultiConfig {
        largerConfig?.progressType = progressType
        return this
    }

    override fun setProgressLoaderUse(use: Boolean): ImageMultiConfig {
        largerConfig?.progressLoaderUse = use
        return this
    }

    override fun setCustomListener(
        layoutId: Int,
        fullViewId: Int,
        listener: OnCustomImageLoadListener?
    ): ImageMultiConfig {
        largerConfig?.layoutId = layoutId
        largerConfig?.fullViewId = fullViewId
        largerConfig?.customImageLoadListener = listener
        return this
    }

    override fun setCustomListener(
        layoutId: Int,
        fullViewId: Int,
        progressId: Int,
        listener: OnCustomImageLoadListener?
    ): ImageMultiConfig {
        largerConfig?.progressId = progressId
        largerConfig?.layoutId = layoutId
        largerConfig?.fullViewId = fullViewId
        largerConfig?.customImageLoadListener = listener
        return this
    }

    override fun setUpCanMove(canMove: Boolean): ImageMultiConfig {
        largerConfig?.upCanMove = canMove
        return this
    }

    override fun setLoadNextFragment(auto: Boolean): ImageMultiConfig {
        largerConfig?.loadNextFragment = auto
        return this
    }

    override fun setDebug(debug: Boolean): ImageMultiConfig {
        largerConfig?.debug = debug
        return this
    }

    override fun setBackgroundColor(color: Int): ImageMultiConfig {
        largerConfig?.backgroundColor = color
        return this
    }

    override fun setOrientation(orientation: Orientation): ImageMultiConfig {
        largerConfig?.orientation = orientation
        return this
    }

    override fun setIndex(pos: Int): ImageMultiConfig {
        largerConfig?.position = pos
        return this
    }

    override fun setDuration(duration: Long): ImageMultiConfig {
        largerConfig?.duration = duration
        return this
    }


}