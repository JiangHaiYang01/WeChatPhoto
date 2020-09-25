package com.starot.larger.fragment

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.lifecycle.MutableLiveData
import androidx.viewpager2.widget.ViewPager2
import com.starot.larger.Larger
import com.starot.larger.R
import com.starot.larger.bean.LargerBean
import com.starot.larger.enums.AnimStatus
import com.starot.larger.enums.AnimType
import com.starot.larger.enums.LoadImageStatus
import com.starot.larger.impl.OnImageCacheListener
import com.starot.larger.impl.OnImageLoadReadyListener
import com.starot.larger.status.LargerStatus
import com.starot.larger.utils.LogUtils
import com.starot.larger.view.image.LargerImageView
import com.starot.larger.view.image.OnLargerDragListener
import com.starot.larger.view.image.OnLargerScaleListener
import com.starot.larger.view.progress.ImageProgressLoader
import kotlin.math.abs

class ImageFg : BaseLargerFragment<LargerBean>(), OnLargerDragListener, OnLargerScaleListener {

    private lateinit var fullView: LargerImageView

    //图片加载框
    private var progressLoader = ImageProgressLoader(getProgressLoaderType())

    //使用liveData 记录 加载进度变化
    private var progressStatusChangeLiveData: MutableLiveData<Int> = MutableLiveData()

    //使用liveData 记录 加载变化
    private var progressLoadChangeLiveData: MutableLiveData<LoadImageStatus> = MutableLiveData()


    override fun getLayoutId(): Int {
        return Larger.largerConfig?.layoutId ?: R.layout.fg_image
    }

    //加载框的id
    override fun getProgressId(): Int {
        return Larger.largerConfig?.progressId ?: R.id.image_progress
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
            Larger.largerConfig?.imageLoad?.load(thumbnailsUrl, position, false, fullView)
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

    override fun onScaleStart() {
        super.onScaleStart()
        LargerStatus.status.value = (AnimStatus.SCALE_START)
    }

    override fun onScaleEnd() {
        super.onScaleEnd()
        //兼容处理 防止在缩放最小状态变成标准状态的动画过程中 触发了 偷懒一下 做一个延迟任务即可
        handler.postDelayed({
            LargerStatus.status.value = (AnimStatus.SCALE_END)
        }, getDuration())

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
            Larger.largerConfig?.customImageLoadListener?.onDoBefore(
                Larger.largerConfig?.imageLoad, fragmentView, position, data
            )

            //注册监听liveData
            registerLiveData(view, position)

            fullView.scaleType = ImageView.ScaleType.FIT_CENTER


            //这里使用的是改写 PhotoView 的
            if (fullView is LargerImageView) {
                this.fullView = fullView
                fullView.setOnLargerDragListener(this)
                fullView.setOnLargerScaleListener(this)
                //设置动画时长
                fullView.setZoomTransitionDuration(getDuration().toInt())

                //设置缩放范围
                fullView.maximumScale = getMaxScale()
                fullView.mediumScale = getMediumScale()

                //单点击
                fullView.setOnViewTapListener { _, _, _ ->
                    //动画过程不给点击
                    if (isAnimIng()) return@setOnViewTapListener
                    LogUtils.i("单点击 准备退出")
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

            //放给开发者自行处理
            Larger.largerConfig?.customImageLoadListener?.onDoAfter(
                Larger.largerConfig?.imageLoad, fragmentView, position, data
            )


            fullView.scaleType = ImageView.ScaleType.FIT_CENTER

            //判断是否有缓存
            Larger.largerConfig?.imageLoad?.checkCache(fullUrl, object : OnImageCacheListener {
                override fun onCache(hasCache: Boolean) {
                    LogUtils.i("url:$fullUrl 是否有缓存:$hasCache")
                    if (hasCache) {
                        //有缓存直接加载
                        Larger.largerConfig?.imageLoad?.load(fullUrl, position, true, fullView)
                    } else {
                        //自动加载
                        if (isAutomatic()) {
                            //显示加载弹窗
                            progressLoadChangeLiveData.value = LoadImageStatus.LOAD_SHOW
                            //加载大图
                            Larger.largerConfig?.imageLoad?.load(fullUrl, position, true, fullView)
                        }
                    }
                }
            })
        }
    }

    override fun onDrag(x: Float, y: Float) {
        if (isAnimIng()) return
//        LogUtils.i("onDrag x:$x y:$y")
        startDrag(fragmentView, fullView, x = x, y = y)
    }

    override fun onDragEnd() {
        LogUtils.i("onDragEnd")
        LargerStatus.status.value = (AnimStatus.DRAG_END)
        endDrag(fullView)
    }

    override fun onDragPrepare(dx: Float, dy: Float): Boolean {
//        LogUtils.i("onDragPrepare dx $dx dy $dy")
        //动画过程中不能触发drag
        if (isAnimIng()) {
            return false
        }
        //图片处于缩放状态
        if (fullView.scale != 1f) {
            return false
        }

        //正在显示加载框 不给drag
        if (progressLoadChangeLiveData.value == LoadImageStatus.LOAD_SHOW) {
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
        LogUtils.i("onDragStart")
        LargerStatus.status.value = (AnimStatus.DRAG_START)
    }


    private fun registerLiveData(view: View, position: Int) {
        //将监听变化的liveData 通过接口保存
        Larger.largerConfig?.imageLoad?.onPrepareLoadProgress(
            progressStatusChangeLiveData,
            position
        )
        //将监听变化的liveData
        Larger.largerConfig?.imageLoad?.onPrepareProgressView(progressLoadChangeLiveData, position)

        //对于加载进度条的逻辑判断
        progressStatusChangeLiveData.observe(this, {
            val progressId = getProgressId()
            if (Larger.largerConfig?.progressLoaderUse == true) {
                progressLoader.onLoadProgress(view, progressId, it, this.position)

                //放给开发者自行处理
                Larger.largerConfig?.customImageLoadListener?.onLoadProgress(
                    view, progressId, it, this.position
                )
            }

        })

        //监听 加载大图进度变化
        progressLoadChangeLiveData.observe(this, {
            //大图加载进度
            val context = context
            if (context != null) {
                val progressId = getProgressId()

                progressLoader.onProgressChange(
                    context,
                    view,
                    progressId,
                    it,
                    this.position
                )

                //放给开发者自行处理
                Larger.largerConfig?.customImageLoadListener?.onProgressChange(
                    context,
                    view,
                    progressId,
                    it,
                    this.position
                )
            }
        })
    }

    override fun onAlreadyLoad(
        data: LargerBean?,
        fullView: View?,
        thumbnailView: View?,
        position: Int,
        view: View,
        success: (LargerBean) -> Unit
    ) {
        if (fullView is ImageView && data != null) {
            val thumbnailsUrl = data.thumbnailsUrl
            if (thumbnailsUrl.isNullOrEmpty()) {
                return
            }
            Larger.largerConfig?.imageLoad?.load(
                thumbnailsUrl,
                position,
                fullView,
                object : OnImageLoadReadyListener {
                    override fun onReady() {
                        success(data)
                    }
                })
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        LogUtils.i("imageFragment create $position")
        //创建的时候 加载框就不可见
        view.findViewById<View>(getProgressId())?.visibility = View.INVISIBLE
        //添加到lifecycle
        lifecycle.addObserver(progressLoader)
    }

    override fun onDestroy() {
        super.onDestroy()
        LogUtils.i("imageFragment destroy $position")
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        LogUtils.i("index:$position isVisibleToUser:$isVisibleToUser")
    }
}