package com.starot.larger.fragment

import android.view.View
import android.widget.ImageView
import com.starot.larger.Larger
import com.starot.larger.R
import com.starot.larger.bean.LargerBean
import com.starot.larger.enums.AnimType
import com.starot.larger.utils.LogUtils

class ImageFg : BaseLargerFragment<LargerBean>() {
    override fun getLayoutId(): Int {
        return Larger.largerConfig?.layoutId ?: R.layout.fg_image
    }

    override fun onTranslatorBefore(type: AnimType, fullView: View, thumbnailView: View) {
        if (fullView is ImageView && thumbnailView is ImageView && type == AnimType.ENTER) {
            LogUtils.i("将大图的 image scale type 设置成 和小图一样的 ${thumbnailView.scaleType}")
            val data = getData() ?: return
            LogUtils.i("加载缩略图")
            //加载缩略图
            val thumbnailsUrl = data.thumbnailsUrl
            if (thumbnailsUrl.isNullOrEmpty()) {
                return
            }
            Larger.largerConfig?.imageLoad?.load(thumbnailsUrl, false, fullView)
            fullView.scaleType = thumbnailView.scaleType
        }
    }

    override fun onTranslatorStart(type: AnimType, fullView: View, thumbnailView: View) {
        if (fullView is ImageView && type == AnimType.ENTER) {
            fullView.scaleType = ImageView.ScaleType.FIT_CENTER
        } else if (fullView is ImageView && thumbnailView is ImageView && (type == AnimType.EXIT || type == AnimType.DRAG_EXIT)) {
            fullView.scaleType = thumbnailView.scaleType
        }
    }

    override fun getFullViewId(): Int {
        return Larger.largerConfig?.fullViewId ?: R.id.image
    }

    override fun onDoBefore(data: LargerBean?, fullView: View?,thumbnailView: View?, position: Int, view: View) {
        if (fullView is ImageView && data != null) {
            val thumbnailsUrl = data.thumbnailsUrl
            if (thumbnailsUrl.isNullOrEmpty()) {
                return
            }
            fullView.scaleType = ImageView.ScaleType.FIT_CENTER
            Larger.largerConfig?.imageLoad?.load(thumbnailsUrl, false, fullView)
        }
    }

    override fun onDoAfter(data: LargerBean?, fullView: View?, thumbnailView: View?,position: Int, view: View) {
        if (fullView is ImageView && data != null) {
            val thumbnailsUrl = data.fullUrl
            if (thumbnailsUrl.isNullOrEmpty()) {
                return
            }
            fullView.scaleType = ImageView.ScaleType.FIT_CENTER
            Larger.largerConfig?.imageLoad?.load(thumbnailsUrl, true, fullView)
        }
    }


}