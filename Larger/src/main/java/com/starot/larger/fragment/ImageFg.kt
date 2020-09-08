package com.starot.larger.fragment

import android.view.View
import android.widget.ImageView
import com.starot.larger.Larger
import com.starot.larger.R
import com.starot.larger.bean.LargerBean
import com.starot.larger.enums.AnimStatus
import com.starot.larger.enums.AnimType
import com.starot.larger.image.LargerImageView
import com.starot.larger.image.OnLargerDragListener
import com.starot.larger.status.LargerStatus
import com.starot.larger.utils.LogUtils
import kotlinx.android.synthetic.main.activity_larger_base.*

class ImageFg : BaseLargerFragment<LargerBean>(), OnLargerDragListener {

    private lateinit var fullView: LargerImageView

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

            //这里使用的是改写 PhotoView 的
            if (fullView is LargerImageView) {
                this.fullView = fullView
                fullView.setOnLargerDragListener(this)


                //动画全部结束以后才能够触发放大缩小的功能
                LargerStatus.status.observe(this, {
                    when (it) {
                        AnimStatus.ENTER_END, AnimStatus.EXIT_END -> {
                            fullView.setCustomZoomable(true)
                        }
                    }
                })

            }
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

    override fun onDoBefore(
        data: LargerBean?,
        fullView: View?,
        thumbnailView: View?,
        position: Int,
        view: View
    ) {
        if (fullView is ImageView && data != null) {
            val thumbnailsUrl = data.thumbnailsUrl
            if (thumbnailsUrl.isNullOrEmpty()) {
                return
            }
            fullView.scaleType = ImageView.ScaleType.FIT_CENTER
            Larger.largerConfig?.imageLoad?.load(thumbnailsUrl, false, fullView)
        }
    }

    override fun onDoAfter(
        data: LargerBean?,
        fullView: View?,
        thumbnailView: View?,
        position: Int,
        view: View
    ) {
        if (fullView is ImageView && data != null) {
            val thumbnailsUrl = data.fullUrl
            if (thumbnailsUrl.isNullOrEmpty()) {
                return
            }
            fullView.scaleType = ImageView.ScaleType.FIT_CENTER
            Larger.largerConfig?.imageLoad?.load(thumbnailsUrl, true, fullView)
        }
    }

    override fun onDrag(x: Float, y: Float) {
        if (isAnimIng()) return
        LogUtils.i("onDrag")
        startDrag(fragmentView, fullView, x = x, y = y)
    }

    override fun onDragEnd() {
        LogUtils.i("onDragEnd")
        LargerStatus.status.postValue(AnimStatus.DRAG_END)
        endDrag(fullView)
    }

    override fun onDragPrepare(): Boolean {
        //动画过程中不能触发drag
        if (isAnimIng()) {
            return false
        }
        //图片处于缩放状态
        if (fullView.scale != 1f) {
            return false
        }
        return true
    }

    override fun onDragStart() {
        LogUtils.i("onDragStart")
        LargerStatus.status.postValue(AnimStatus.DRAG_START)
    }


}