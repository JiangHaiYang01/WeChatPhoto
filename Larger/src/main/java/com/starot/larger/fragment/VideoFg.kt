package com.starot.larger.fragment

import android.view.View
import android.widget.ImageView
import com.starot.larger.Larger
import com.starot.larger.R
import com.starot.larger.bean.LargerBean
import com.starot.larger.enums.AnimType
import com.starot.larger.utils.LogUtils

class VideoFg : BaseLargerFragment<LargerBean>() {
    override fun getLayoutId(): Int {
        return Larger.largerConfig?.layoutId ?: Larger.largerConfig?.videoLoad?.getVideoLayoutId()
        ?: -1
    }

    override fun onTranslatorBefore(type: AnimType, fullView: View, thumbnailView: View) {


    }

    override fun onTranslatorStart(type: AnimType, fullView: View, thumbnailView: View) {

    }

    override fun getFullViewId(): Int {
        return Larger.largerConfig?.fullViewId ?: Larger.largerConfig?.videoLoad?.getVideoViewId()
        ?: -1
    }

    override fun onDoBefore(data: LargerBean?, fullView: View?, position: Int, view: View) {

    }

    override fun onDoAfter(data: LargerBean?, fullView: View?, position: Int, view: View) {
        if (data != null) {
            val fullUrl = data.fullUrl
            if (fullUrl != null)
                Larger.largerConfig?.videoLoad?.load(fullUrl, view)
        }
    }


}