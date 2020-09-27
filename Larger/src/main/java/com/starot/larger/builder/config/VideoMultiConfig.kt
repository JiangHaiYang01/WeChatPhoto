package com.starot.larger.builder.config

import android.content.Context
import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import com.starot.larger.act.CustomLargerAct
import com.starot.larger.act.LargerAct
import com.starot.larger.builder.impl.ImageConfig
import com.starot.larger.builder.impl.MultiConfig
import com.starot.larger.builder.impl.VideoConfig
import com.starot.larger.config.LargerConfig
import com.starot.larger.enums.LargerEnum
import com.starot.larger.enums.Orientation
import com.starot.larger.impl.OnCustomImageLoadListener
import com.starot.larger.impl.OnImageLoadListener
import com.starot.larger.impl.OnLargerType
import com.starot.larger.impl.OnVideoLoadListener
import com.starot.larger.view.progress.ImageProgressLoader

class VideoMultiConfig(private val largerConfig: LargerConfig?) :
    MultiConfig<VideoMultiConfig>, VideoConfig<VideoMultiConfig> {

    init {
        largerConfig?.largerType = LargerEnum.LISTS
    }

    override fun setRecyclerView(recyclerView: RecyclerView): VideoMultiConfig {
        largerConfig?.recyclerView = recyclerView
        return this
    }

    override fun setData(data: List<OnLargerType>): VideoMultiConfig {
        largerConfig?.data = data
        return this
    }


    override fun setImageLoad(imageLoad: OnImageLoadListener): VideoMultiConfig {
        largerConfig?.imageLoad = imageLoad
        return this
    }


    override fun setUpCanMove(canMove: Boolean): VideoMultiConfig {
        largerConfig?.upCanMove = canMove
        return this
    }

    override fun setLoadNextFragment(auto: Boolean): VideoMultiConfig {
        largerConfig?.loadNextFragment = auto
        return this
    }

    override fun setDebug(debug: Boolean): VideoMultiConfig {
        largerConfig?.debug = debug
        return this
    }

    override fun setBackgroundColor(color: Int): VideoMultiConfig {
        largerConfig?.backgroundColor = color
        return this
    }

    override fun setOrientation(orientation: Orientation): VideoMultiConfig {
        largerConfig?.orientation = orientation
        return this
    }

    override fun setIndex(pos: Int): VideoMultiConfig {
        largerConfig?.position = pos
        return this
    }

    override fun setDuration(duration: Long): VideoMultiConfig {
        largerConfig?.duration = duration
        return this
    }

    override fun setVideoLoad(videoLoader: OnVideoLoadListener): VideoMultiConfig {
        largerConfig?.videoLoad = videoLoader
        return this
    }


}