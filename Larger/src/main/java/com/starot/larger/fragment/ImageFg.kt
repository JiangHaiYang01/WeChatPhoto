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
import com.starot.larger.enums.AnimType
import com.starot.larger.utils.LogUtils

class ImageFg : BaseLargerFragment<ImageBean>() {
    override fun getLayoutId(): Int {
        return Larger.largerConfig?.layoutId ?: R.layout.fg_image
    }

    override fun onTranslatorBefore(type: AnimType, fullView: View, thumbnailView: View) {
        if (fullView is ImageView && thumbnailView is ImageView && type == AnimType.ENTER) {
            LogUtils.i("将大图的 image scale type 设置成 和小图一样的 ${thumbnailView.scaleType}")
            val data = getData() ?: return
            LogUtils.i("加载缩略图")
            //加载缩略图
            Larger.largerConfig?.imageLoad?.load(data.thumbnailsUrl, false, fullView)
            fullView.scaleType = thumbnailView.scaleType
        }
    }

    override fun onTranslatorStart(type: AnimType, fullView: View, thumbnailView: View) {
        if (fullView is ImageView && type == AnimType.ENTER) {
            fullView.scaleType = ImageView.ScaleType.FIT_CENTER
        } else if (fullView is ImageView && thumbnailView is ImageView && type == AnimType.EXIT) {
            fullView.scaleType = thumbnailView.scaleType
        }
    }

    override fun getFullViewId(): Int {
        return Larger.largerConfig?.fullViewId ?: R.id.image
    }

    override fun onLoad(data: ImageBean?, fullView: View?, position: Int) {
        if (fullView is ImageView && data != null) {
            fullView.scaleType = ImageView.ScaleType.FIT_CENTER
            Larger.largerConfig?.imageLoad?.load(data.thumbnailsUrl, false, fullView)
        }
    }


}