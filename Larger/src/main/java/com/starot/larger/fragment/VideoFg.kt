package com.starot.larger.fragment

import android.view.View
import android.widget.ImageView
import androidx.viewpager2.widget.ViewPager2
import com.starot.larger.Larger
import com.starot.larger.bean.LargerBean
import com.starot.larger.enums.AnimStatus
import com.starot.larger.enums.AnimType
import com.starot.larger.view.image.OnLargerDragListener
import com.starot.larger.impl.OnImageLoadReadyListener
import com.starot.larger.status.LargerStatus
import com.starot.larger.utils.LogUtils
import kotlin.math.abs

class VideoFg : BaseLargerFragment<LargerBean>(), OnLargerDragListener {

    private lateinit var videoView: View


    private var isLoadVideo = false

    override fun getLayoutId(): Int {
        return Larger.largerConfig?.layoutId ?: Larger.largerConfig?.videoLoad?.getVideoLayoutId()
        ?: -1
    }


    override fun onTranslatorBefore(type: AnimType, fullView: View, thumbnailView: View) {
        val imageView = getPoster(fragmentView)
        if (imageView != null && thumbnailView is ImageView && type == AnimType.ENTER) {
            val data = getData() ?: return
            val thumbnailsUrl = data.thumbnailsUrl
            if (thumbnailsUrl.isNullOrEmpty()) {
                return
            }
            Larger.largerConfig?.imageLoad?.load(thumbnailsUrl, position, false, imageView)
            imageView.scaleType = thumbnailView.scaleType
        }
    }

    override fun onTranslatorStart(type: AnimType, fullView: View, thumbnailView: View) {
        val imageView = getPoster(fragmentView)
        if (imageView is ImageView && type == AnimType.ENTER) {
            imageView.scaleType = ImageView.ScaleType.FIT_CENTER
        } else if (imageView is ImageView && thumbnailView is ImageView && (type == AnimType.EXIT || type == AnimType.DRAG_EXIT)) {
            imageView.scaleType = thumbnailView.scaleType
        }
    }


    override fun getFullViewId(): Int {
        return Larger.largerConfig?.fullViewId ?: Larger.largerConfig?.videoLoad?.getVideoViewId()
        ?: -1
    }

    override fun onDoBefore(
        data: LargerBean?,
        fullView: View?,
        thumbnailView: View?,
        position: Int,
        view: View
    ) {
        if (fullView != null) {
            this.videoView = fullView
        }

        val imageView = getPoster(view)
        if (imageView != null && data != null) {
            val thumbnailsUrl = data.thumbnailsUrl
            if (thumbnailsUrl.isNullOrEmpty()) {
                return
            }
            imageView.scaleType = ImageView.ScaleType.FIT_CENTER
//            Larger.largerConfig?.imageLoad?.load(thumbnailsUrl, false, imageView)
        }
    }

    private fun getPoster(view: View): ImageView? {
        return Larger.largerConfig?.videoLoad?.getPoster(view)
    }

    override fun onDoAfter(
        data: LargerBean?,
        fullView: View?,
        thumbnailView: View?,
        position: Int,
        view: View
    ) {
        LogUtils.i("video onDoAfter")
        if (data != null) {
            val poster = getPoster(view)
            if (poster != null) {
                poster.scaleType = ImageView.ScaleType.FIT_CENTER
            }
            if (isLoadVideo) {
                LogUtils.i("isLoadVideo ")
                return
            }
            isLoadVideo = true
            Larger.largerConfig?.videoLoad?.loadVideo(data, view, this)
        }

    }


    override fun onDrag(x: Float, y: Float) {
        if (isAnimIng()) return
        LogUtils.i("video drag------------------")
        startDrag(fragmentView, videoView, x = x, y = y)
    }

    override fun onDragEnd() {
        LargerStatus.status.value = (AnimStatus.DRAG_END)
        endDrag(videoView)
    }

    override fun onDragPrepare(dx: Float, dy: Float): Boolean {
        //动画过程中不能触发drag
        if (isAnimIng()) {
            return false
        }

        if (getOrientation() == ViewPager2.ORIENTATION_HORIZONTAL) {
            //一开始向上无效
            if (abs(dy) > abs(dx)) {
                //默认是可以不可以直接向上滑的
                val upCanMove = Larger.largerConfig?.upCanMove
                if (upCanMove == null || upCanMove == false) {
                    if (dy < 0) {
                        return false
                    }
                }
                return true
            }
        } else {
            if (abs(dx) > abs(dy)) {
                return true
            }
        }

        return false
    }

    override fun onDragStart() {
        LargerStatus.status.value = (AnimStatus.DRAG_START)
    }

    override fun onAlreadyLoad(
        data: LargerBean?,
        fullView: View?,
        thumbnailView: View?,
        position: Int,
        view: View,
        success: (LargerBean) -> Unit
    ) {
        val imageView = getPoster(view)
        if (imageView != null && data != null) {
            val thumbnailsUrl = data.thumbnailsUrl
            if (thumbnailsUrl.isNullOrEmpty()) {
                return
            }
            Larger.largerConfig?.imageLoad?.load(
                thumbnailsUrl,
                position,
                imageView,
                object : OnImageLoadReadyListener {
                    override fun onReady() {
                        LogUtils.i("onReady")
                        success(data)
                    }
                })
        }
    }


}