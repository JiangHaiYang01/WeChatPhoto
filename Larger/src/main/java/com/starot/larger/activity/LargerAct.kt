package com.starot.larger.activity

import android.animation.Animator
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.starot.larger.R
import com.starot.larger.adapter.ViewPagerAdapter
import com.starot.larger.anim.LargerAnim
import com.starot.larger.bean.ImageInfo
import com.starot.larger.tools.ColorTool
import com.starot.larger.tools.ImageTool
import com.starot.larger.view.image.OnViewDragListener
import com.starot.larger.view.image.PhotoView
import kotlinx.android.synthetic.main.activity_larger_base.*
import java.util.*
import kotlin.math.abs


abstract class LargerAct<T> : AppCompatActivity() {


    //默认显示时间
    private var defDuration: Long = 200

    //当前的index
    private var mCurrentIndex = 0

    //默认拖动时候的阻尼系数   [1.0----4.0f]
    private var damping: Float = 1.0f

    //是否发生过移动
    private var isDrag = false

    //动画是都已经播放过了
    private var isFinishAnim = false

    companion object {
        const val IMAGE = "images"
        const val INDEX = "index"
        const val ORIGINAL = "ORIGINAL"
        const val TAG = ImageTool.TAG
    }

    //图片缩放比例
    private var currentOriginalScale = 0f

    //根据外部布局 传入的id  获取到的 PhotoView
    private lateinit var image: PhotoView

    //是否正在动画
    private var isAnimIng = false

    //进入动画效果
    private var enterAnimListener = object : Animator.AnimatorListener {
        override fun onAnimationRepeat(p0: Animator?) {

        }

        override fun onAnimationEnd(p0: Animator?) {
            Log.i(TAG, "进入的动画 end")
            setViewPagerEnable(true)
            isAnimIng = false
        }

        override fun onAnimationCancel(p0: Animator?) {
        }

        override fun onAnimationStart(p0: Animator?) {
            Log.i(TAG, "进入的动画 start")
            setViewPagerEnable(false)
            isAnimIng = true
        }
    }

    //退出的动画效果
    private var exitAnimListener = object : Animator.AnimatorListener {
        override fun onAnimationRepeat(p0: Animator?) {

        }

        override fun onAnimationEnd(p0: Animator?) {
            Log.i(TAG, "退出的动画 end")
            isAnimIng = false
            setViewPagerEnable(true)
            finish()
            overridePendingTransition(0, 0)
        }

        override fun onAnimationCancel(p0: Animator?) {
        }

        override fun onAnimationStart(p0: Animator?) {
            setViewPagerEnable(false)
            Log.i(TAG, "退出的动画 start")
            isAnimIng = true
        }
    }

    //item viewHolder
    private lateinit var viewHolder: ViewPagerAdapter.PhotoViewHolder
    override fun onCreate(savedInstanceState: Bundle?) {
        overridePendingTransition(0, 0)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_larger_base)
        mCurrentIndex = intent.getIntExtra(INDEX, 0)
        setAdapter()
    }

    private fun setAdapter() {
        val data = getData()
        larger_viewpager.adapter = ViewPagerAdapter(
            data,
            getItemLayout(),
            object : ViewPagerAdapter.OnBindViewHolderListener {
                override fun onBindViewHolder(
                    holder: ViewPagerAdapter.PhotoViewHolder,
                    position: Int
                ) {
                    viewHolder = holder
                    Log.i(TAG, "onBindViewHolder")
                    val itemView = holder.itemView
                    if (data == null) {
                        item(itemView, position, null)
                    } else {
                        item(itemView, position, data[position])
                    }
                    startEnterAnim()
                }
            })

        //不需要平滑过渡了
        larger_viewpager.setCurrentItem(getCurrentItemIndex(), false)

        larger_viewpager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                mCurrentIndex = position
            }
        })
    }

    //启动小图片 到大图的动画效果
    private fun startEnterAnim() {
        //防止多次启动
        if (!isFinishAnim) {
            val info = getImageInfo()[getCurrentItemIndex()]
            //当前的图片 模式 做了裁剪或者center 等 处理
            val originalScale =
                ImageTool.getCurrentPicOriginalScale(applicationContext, info)
            if (info.isCenter) {

                LargerAnim.startEnter(
                    larger_parent,
                    originalScale,
                    setDuration(),
                    getPhotoViewId(),
                    viewHolder,
                    getCurrentItemIndex(),
                    enterAnimListener

                )
            } else {
                LargerAnim.startEnter(
                    this,
                    larger_parent,
                    larger_viewpager,
                    originalScale,
                    info,
                    setDuration(),
                    enterAnimListener
                )
            }
            isFinishAnim = true
        }
    }


    //设置viewpager 是否可以滑动
    private fun setViewPagerEnable(isUserInputEnabled: Boolean) {
        larger_viewpager.isUserInputEnabled = isUserInputEnabled //true:滑动，false：禁止滑动
    }


    private fun item(itemView: View, position: Int, data: T?) {
        try {
            image = itemView.findViewById(getPhotoViewId())
        } catch (t: Throwable) {
            throw Throwable("Must add  PhotoView in your layout")
        }
        item(itemView, image, position, data)

        //单点击退出
        image.setOnPhotoTapListener { _, _, _ ->
            exitAnim()
        }

        //长按
        image.setOnLongClickListener {
            onLongClickListener()
            return@setOnLongClickListener false
        }


        //移动
        image.setOnViewDragListener(object : OnViewDragListener {
            override fun onDrag(dx: Float, dy: Float) {

            }

            override fun onScroll(x: Float, y: Float) {
                //这里 需要判断一下滑动的角度  防止和 viewpager 滑动冲突
                if (isDrag) {
                    //正在播放动画 不给滑动
                    if (isAnimIng) {
                        return
                    }
                    //图片处于缩放状态
                    if (image.scale != 1.0f) {
                        return
                    }
                    startDrag(x, y)
                } else {
                    if (abs(x) > 30 && abs(y) > 60) {
                        //正在播放动画 不给滑动
                        if (isAnimIng) {
                            return
                        }
                        //图片处于缩放状态
                        if (image.scale != 1.0f) {
                            return
                        }
                        // 一开始向上滑动无效的
                        if (y > 0) {
                            isDrag = true
                            startDrag(x, y)
                        }
                    }
                }
            }

            override fun onScrollFinish() {
                if (isDrag)
                    onDragFinish()
            }

            override fun onScrollStart() {
                isDrag = false
            }
        })
    }


    //是否可以方法缩小 移动过程中不可以方法缩小
    private fun setZoomable(isZoomable: Boolean) {
        image.setCustomZoomable(isZoomable)
    }


    //当前选中的下标
    private fun getCurrentItemIndex(): Int {
        return mCurrentIndex
    }

    //保存的图片信息
    private fun getImageInfo(): ArrayList<ImageInfo> {
        val data = intent.getParcelableArrayListExtra<ImageInfo>(ORIGINAL)
        if (data != null)
            return data
        else
            throw   Throwable("Please set the putParcelableArrayListExtra; TAG is the ORIGINAL data set, which contains the information of the small graph")
    }


    override fun onBackPressed() {
        //点击返回无效
    }

    //退出动画
    open fun exitAnim() {

        //如果现在是放大的  仿造微信 就先变成真长 1：1 比例 在缩小
        if (image.scale > 1.0f) {
            image.scale = 1f
        }


        val info = getImageInfo()[getCurrentItemIndex()]
        val originalScale =
            ImageTool.getCurrentPicOriginalScale(this, info)
        if (info.isCenter) {
            LargerAnim.startExit(
                larger_parent,
                originalScale,
                setDuration(),
                getPhotoViewId(),
                viewHolder,
                getCurrentItemIndex(),
                exitAnimListener

            )
        } else {
            LargerAnim.startExit(
                this,
                larger_parent,
                larger_viewpager,
                originalScale,
                info,
                setDuration(),
                exitAnimListener
            )
        }
    }

    private fun onDragFinish() {
        setViewPagerEnable(true)
        setZoomable(true)
//        Log.i(TAG, "drag finish ${larger_viewpager.scaleX}")
        if (larger_viewpager.scaleX > 0.7f) {
            LargerAnim.dragFinish(
                larger_parent,
                larger_viewpager,
                currentOriginalScale,
                setDuration()
            )
        } else {
            val info = getImageInfo()[getCurrentItemIndex()]
            val originalScale =
                ImageTool.getCurrentPicOriginalScale(this, info)
            LargerAnim.startExitDrag(
                this,
                larger_parent,
                larger_viewpager,
                originalScale,
                currentOriginalScale,
                info,
                setDuration(),
                exitAnimListener
            )
        }
    }


    //拖动
    private fun startDrag(x: Float, y: Float) {
        setViewPagerEnable(false)
        setZoomable(false)
        var dampingData = setDamping()
        if (dampingData >= 4.0f) {
            dampingData = 4.0f
        } else if (dampingData < 0f) {
            dampingData = 0f
        }

        larger_viewpager.translationX = x * dampingData
        larger_viewpager.translationY = y * dampingData
        if (y > 0) {
            larger_viewpager.pivotX = (ImageTool.getWindowWidth(this) / 2).toFloat()
            larger_viewpager.pivotY = (ImageTool.getWindowHeight(this) / 2).toFloat()
            val scale: Float = abs(y) / ImageTool.getWindowHeight(this)
            if (scale < 1 && scale > 0) {
                larger_viewpager.scaleX = 1 - scale
                larger_viewpager.scaleY = 1 - scale
                currentOriginalScale = 1 - scale
                larger_parent.setBackgroundColor(
                    ColorTool.getColorWithAlpha(Color.BLACK, 1 - scale)
                )
            }
        } else {
            currentOriginalScale = 1.0f
            larger_viewpager.pivotX = (ImageTool.getWindowWidth(this) / 2).toFloat()
            larger_viewpager.pivotY = (ImageTool.getWindowHeight(this) / 2).toFloat()
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        LargerAnim.imageViews.clear()
    }


    //设置显示时间
    open fun setDuration(): Long {
        return defDuration
    }

    //拖动时候的阻尼系数
    open fun setDamping(): Float {
        return damping
    }

    //长按的事件
    open fun onLongClickListener() {}

    //默认的布局
    abstract fun getItemLayout(): Int

    //获取 PhotoViewId
    abstract fun getPhotoViewId(): Int

    //数据源
    abstract fun getData(): List<T>?

    abstract fun item(itemView: View, photoView: PhotoView, position: Int, data: T?)


}
