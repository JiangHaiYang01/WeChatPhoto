package com.starot.larger.fragment

import android.view.View
import android.widget.ImageView
import androidx.lifecycle.MutableLiveData
import com.starot.larger.Larger
import com.starot.larger.R
import com.starot.larger.bean.LargerBean
import com.starot.larger.enums.AnimStatus
import com.starot.larger.enums.AnimType
import com.starot.larger.image.LargerImageView
import com.starot.larger.image.OnLargerDragListener
import com.starot.larger.impl.OnImageCacheListener
import com.starot.larger.status.LargerStatus
import com.starot.larger.utils.LogUtils
import kotlinx.android.synthetic.main.activity_larger_base.*
import kotlin.math.abs

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

            //放给开发者自行处理
            Larger.largerConfig?.customImageLoadListener?.onCustomImageLoad(
                fragmentView, position, data
            )

            fullView.scaleType = ImageView.ScaleType.FIT_CENTER
            Larger.largerConfig?.imageLoad?.load(thumbnailsUrl, false, fullView)


            //这里使用的是改写 PhotoView 的
            if (fullView is LargerImageView) {
                this.fullView = fullView
                fullView.setOnLargerDragListener(this)
                //设置动画时长
                fullView.setZoomTransitionDuration(getDuration().toInt())

                //设置缩放范围
                fullView.maximumScale = getMaxScale()
                fullView.mediumScale = getMediumScale()

                //单点击
                fullView.setOnViewTapListener { _, _, _ ->
                    //动画过程不给点击
                    if (isAnimIng()) return@setOnViewTapListener
                    exitAnimStart(fragmentView, getDuration(), fullView, getThumbnailView(position))
                }

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

    override fun onDoAfter(
        data: LargerBean?,
        fullView: View?,
        thumbnailView: View?,
        position: Int,
        view: View
    ) {

        if (fullView is ImageView && data != null) {
            val fullUrl = data.fullUrl
            if (fullUrl.isNullOrEmpty()) {
                return
            }
            fullView.scaleType = ImageView.ScaleType.FIT_CENTER
            //不自动加载
            if (!isAutomatic()) {
                //判断是否有缓存
                Larger.largerConfig?.imageLoad?.checkCache(fullUrl, object : OnImageCacheListener {
                    override fun onCache(hasCache: Boolean) {
                        LogUtils.i("url:$fullUrl 是否有缓存:$hasCache")
                        if (hasCache) {
                            Larger.largerConfig?.imageLoad?.load(fullUrl, true, fullView)
                        }
                    }
                })
                return
            }
            //不管有没有缓存自动加载
            Larger.largerConfig?.imageLoad?.load(fullUrl, true, fullView)
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

    override fun onDragPrepare(dx: Float, dy: Float): Boolean {
        //动画过程中不能触发drag
        if (isAnimIng()) {
            return false
        }
        //图片处于缩放状态
        if (fullView.scale != 1f) {
            return false
        }

        if (abs(dx) > 30 && abs(dy) > 60) {
            // 一开始向上滑动无效的
            if (dy > 0) {
                return true
            }
        }
        return false
    }

    override fun onDragStart() {
        LogUtils.i("onDragStart")
        LargerStatus.status.postValue(AnimStatus.DRAG_START)
    }


}