package com.starot.larger.fragment

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.starot.larger.Larger
import com.starot.larger.R
import com.starot.larger.anim.AnimEnterHelper
import com.starot.larger.bean.ImageBean
import com.starot.larger.config.LargerConfig
import com.starot.larger.utils.LogUtils

class ImageFg : BaseLargerFragment<ImageBean>() {
    override fun getLayoutId(): Int {
        return Larger.largerConfig?.layoutId ?: R.layout.fg_image
    }

    override fun onTranslatorBefore(fullView: View, thumbnailView: View) {
        if (fullView is ImageView && thumbnailView is ImageView) {
            LogUtils.i("将大图的 image scale type 设置成 和小图一样的 ${thumbnailView.scaleType}")
            fullView.scaleType = thumbnailView.scaleType
        }
    }

    override fun onTranslatorStart(fullView: View, thumbnailView: View) {
        if (fullView is ImageView) {
            fullView.scaleType = ImageView.ScaleType.FIT_CENTER
        }
    }


    override fun getFullViewId(): Int {
        return Larger.largerConfig?.fullViewId ?: R.id.image
    }


    override fun loadBeforeAnimStart(
        config: LargerConfig?,
        data: ImageBean,
        fullView: View,
        view: View
    ) {
        //加载缩略图
        if (fullView is ImageView)
            config?.imageLoad?.load(data.thumbnailsUrl, false, fullView)
    }

}