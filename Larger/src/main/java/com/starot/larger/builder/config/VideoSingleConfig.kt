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
import com.starot.larger.builder.impl.VideoConfig
import com.starot.larger.config.LargerConfig
import com.starot.larger.enums.LargerEnum
import com.starot.larger.enums.Orientation
import com.starot.larger.impl.OnCustomImageLoadListener
import com.starot.larger.impl.OnImageLoadListener
import com.starot.larger.impl.OnLargerType
import com.starot.larger.impl.OnVideoLoadListener
import com.starot.larger.view.progress.ImageProgressLoader

class VideoSingleConfig(private val largerConfig: LargerConfig?) :
    SingleConfig<VideoSingleConfig>, VideoConfig<VideoSingleConfig> {

    init {
        largerConfig?.largerType = LargerEnum.SINGLES
    }


    override fun setImageLoad(imageLoad: OnImageLoadListener): VideoSingleConfig {
        largerConfig?.imageLoad = imageLoad
        return this
    }


    override fun setUpCanMove(canMove: Boolean): VideoSingleConfig {
        largerConfig?.upCanMove = canMove
        return this
    }

    override fun setLoadNextFragment(auto: Boolean): VideoSingleConfig {
        largerConfig?.loadNextFragment = auto
        return this
    }

    override fun setDebug(debug: Boolean): VideoSingleConfig {
        largerConfig?.debug = debug
        return this
    }

    override fun setBackgroundColor(color: Int): VideoSingleConfig {
        largerConfig?.backgroundColor = color
        return this
    }

    override fun setOrientation(orientation: Orientation): VideoSingleConfig {
        largerConfig?.orientation = orientation
        return this
    }


    override fun setDuration(duration: Long): VideoSingleConfig {
        largerConfig?.duration = duration
        return this
    }

    override fun setImage(image: ImageView): VideoSingleConfig {
        largerConfig?.images = arrayListOf(image)
        return this
    }

    override fun setData(data: OnLargerType): VideoSingleConfig {
        largerConfig?.data = arrayListOf(data)
        return this
    }

    override fun setVideoLoad(videoLoader: OnVideoLoadListener): VideoSingleConfig {
        largerConfig?.videoLoad = videoLoader
        return this
    }


}