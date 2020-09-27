package com.starot.larger.builder.config

import android.content.Context
import android.content.Intent
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.starot.larger.act.CustomLargerAct
import com.starot.larger.act.LargerAct
import com.starot.larger.builder.impl.ImageConfig
import com.starot.larger.builder.impl.MultiConfig
import com.starot.larger.builder.impl.SingleConfig
import com.starot.larger.config.LargerConfig
import com.starot.larger.enums.LargerEnum
import com.starot.larger.enums.Orientation
import com.starot.larger.impl.OnCustomImageLoadListener
import com.starot.larger.impl.OnImageLoadListener
import com.starot.larger.impl.OnLargerType
import com.starot.larger.view.progress.ImageProgressLoader

class ImageSingleConfig(private val largerConfig: LargerConfig?) :
    SingleConfig<ImageSingleConfig>, ImageConfig<ImageSingleConfig> {

    init {
        largerConfig?.largerType = LargerEnum.SINGLES
    }

    override fun setAutomatic(automatic: Boolean): ImageSingleConfig {
        largerConfig?.automatic = automatic
        return this
    }

    override fun setMaxScale(maxScale: Float): ImageSingleConfig {
        largerConfig?.maxScale = maxScale
        return this
    }

    override fun setMediumScale(mediumScale: Float): ImageSingleConfig {
        largerConfig?.mediumScale = mediumScale
        return this
    }

    override fun setImageLoad(imageLoad: OnImageLoadListener): ImageSingleConfig {
        largerConfig?.imageLoad = imageLoad
        return this
    }

    override fun setProgressType(progressType: ImageProgressLoader.ProgressType): ImageSingleConfig {
        largerConfig?.progressType = progressType
        return this
    }

    override fun setProgressLoaderUse(use: Boolean): ImageSingleConfig {
        largerConfig?.progressLoaderUse = use
        return this
    }

    override fun setCustomListener(
        layoutId: Int,
        fullViewId: Int,
        listener: OnCustomImageLoadListener?
    ): ImageSingleConfig {
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
    ): ImageSingleConfig {
        largerConfig?.progressId = progressId
        largerConfig?.layoutId = layoutId
        largerConfig?.fullViewId = fullViewId
        largerConfig?.customImageLoadListener = listener
        return this
    }

    override fun setUpCanMove(canMove: Boolean): ImageSingleConfig {
        largerConfig?.upCanMove = canMove
        return this
    }

    override fun setLoadNextFragment(auto: Boolean): ImageSingleConfig {
        largerConfig?.loadNextFragment = auto
        return this
    }

    override fun setDebug(debug: Boolean): ImageSingleConfig {
        largerConfig?.debug = debug
        return this
    }

    override fun setBackgroundColor(color: Int): ImageSingleConfig {
        largerConfig?.backgroundColor = color
        return this
    }

    override fun setOrientation(orientation: Orientation): ImageSingleConfig {
        largerConfig?.orientation = orientation
        return this
    }


    override fun setDuration(duration: Long): ImageSingleConfig {
        largerConfig?.duration = duration
        return this
    }

    override fun setImage(image: ImageView): ImageSingleConfig {
        largerConfig?.images = arrayListOf(image)
        return this
    }

    override fun setData(data: OnLargerType): ImageSingleConfig {
        largerConfig?.data = arrayListOf(data)
        return this
    }


}