package com.starot.larger.act

import android.view.View
import android.widget.ImageView
import android.widget.VideoView
import com.starot.larger.Larger
import com.starot.larger.R
import com.starot.larger.config.SingleLargerConfig
import com.starot.larger.enums.FullType
import com.starot.larger.utils.LogUtils

abstract class SingleLargerAct<T> : LargerAct<T>() {


    var singleConfig: SingleLargerConfig? = null

    override fun beforeCreate() {
        singleConfig = Larger.singleConfig
    }


    override fun getThumbnailView(position: Int): ImageView? {
        val images = singleConfig?.images
        return images?.get(position)
    }


}

