package com.starot.larger.activity

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.starot.larger.R
import com.starot.larger.adapter.ViewPagerAdapter
import com.starot.larger.anim.*
import com.starot.larger.impl.OnAfterTransitionListener
import com.starot.larger.impl.OnAnimatorListener
import com.starot.larger.impl.OnDragAnimListener
import com.starot.larger.tool.ColorTool
import com.starot.larger.tool.ImageDragHelper
import com.starot.larger.tool.ScaleHelper
import com.starot.larger.view.image.PhotoView
import kotlinx.android.synthetic.main.activity_larger_base.*
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min


abstract class LargerAct<T> : AppCompatActivity() {

    companion object {
        const val TAG = "LargerActTAG"
    }


    private val imageDragHelper = ImageDragHelper()

    //进入的动画播放完成了
    private var animFinish = false

    //进入动画效果
    private val enterAnimListener = object : OnAnimatorListener {
        override fun onAnimatorStart() {
            setViewPagerEnable(false)
            imageDragHelper.isAnimIng = true
        }

        override fun onAnimatorEnd() {
            setViewPagerEnable(true)
            imageDragHelper.isAnimIng = false
        }
    }

    //退出的动画效果
    private val exitAnimListener = object : OnAnimatorListener {
        override fun onAnimatorStart() {
            setViewPagerEnable(false)
            imageDragHelper.isAnimIng = true
        }

        override fun onAnimatorEnd() {
            imageDragHelper.isAnimIng = false
            setViewPagerEnable(true)
            finish()
            overridePendingTransition(0, 0)
        }
    }

    //存储 viewHolder
    private val viewHolderMap = HashMap<Int, RecyclerView.ViewHolder>()

    //动画执行以后
    private val afterTransitionListener = object : OnAfterTransitionListener {
        override fun afterTransitionLoad(holder: RecyclerView.ViewHolder) {
            itemBindViewHolder(true, holder.itemView, mCurrentIndex, getData()?.get(mCurrentIndex))
        }
    }

    //viewpager 滑动监听
    private val viewPagerChangeListener = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            mCurrentIndex = position
        }
    }

    //当前的index
    private var mCurrentIndex = 0

    //根视图
    private lateinit var parentView: View

    //适配器
    private val adapter by lazy {
        val data = getData()
        ViewPagerAdapter(
            data,
            getItemLayout(),
            object : ViewPagerAdapter.OnBindViewHolderListener {
                override fun onBindViewHolder(
                    holder: ViewPagerAdapter.PhotoViewHolder,
                    position: Int
                ) {
                    viewHolderMap[position] = holder
                    val itemView = holder.itemView
                    //通用方法
                    item(holder, position, data?.get(position))
                    //用户自己处理加载逻辑
                    itemBindViewHolder(false, itemView, position, data?.get(position))
                    //进入动画
                    enterAnim(holder)
                }
            })
    }


    //图片缩放比例
    private var currentScale = 0f

    //手指移动
    private val onDragListener = object : OnDragAnimListener {
        override fun onStartDrag(image: PhotoView, x: Float, y: Float) {
            setViewPagerEnable(false)
            setZoomable(image, false)
            //默认拖动时候的阻尼系数
            var dampingData = setDamping()
            if (dampingData >= 1.0f) {
                dampingData = 1.0f
            } else if (dampingData < 0f) {
                dampingData = 0f
            }
            //图片的缩放 和位置变化
            val fixedOffsetY = y - 0
            val fraction = abs(max(-1f, min(1f, fixedOffsetY / image.height)))
            val fakeScale = 1 - min(0.4f, fraction)
            image.scaleX = fakeScale
            image.scaleY = fakeScale
            image.translationY = fixedOffsetY * dampingData
            image.translationX = x / 2 * dampingData


            //已经向上了 就黑色背景 不需要改动了
            if (y > 0) {
                //背景的颜色 变化
                val scale: Float = abs(y) / ScaleHelper.getWindowHeight(applicationContext)
                currentScale = 1 - scale
                parentView.setBackgroundColor(
                    ColorTool.getColorWithAlpha(Color.BLACK, 1 - scale)
                )
            }

        }

        override fun onEndDrag(
            image: PhotoView,
            holder: ViewPagerAdapter.PhotoViewHolder
        ) {
            setViewPagerEnable(true)
            setZoomable(image, true)

            var fraction = setFraction()
            if (fraction > 1f) {
                fraction = 1f
            } else if (fraction < 0f) {
                fraction = 0f
            }
            if (abs(image.translationY) < image.height * fraction) {
                AnimDragHelper.start(
                    getPhotoViewId(),
                    setDuration(),
                    image,
                    getImageArrayList()[mCurrentIndex],
                    holder,
                    enterAnimListener,
                    afterTransitionListener
                )
                //背景颜色变化
                AnimBgEnterHelper.start(parentView, currentScale, setDuration())
            } else {
                exitAnim(currentScale, holder)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        overridePendingTransition(0, 0)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_larger_base)
        //根视图
        parentView = larger_parent
        //当前的下标
        mCurrentIndex = getIndex()
        //设置适配器
        larger_viewpager.adapter = adapter
        //不需要平滑过渡了
        larger_viewpager.setCurrentItem(mCurrentIndex, false)
        //viewpager 滑动 index 更改
        larger_viewpager.registerOnPageChangeCallback(viewPagerChangeListener)
    }


    private fun item(holder: ViewPagerAdapter.PhotoViewHolder, position: Int, data: T?) {
        val image = holder.itemView.findViewById<PhotoView>(getPhotoViewId())

        //单点击退出
        image.setOnPhotoTapListener { _, _, _ ->
            onSingleClickListener()
            exitAnim(1.0f, holder)
        }

        //长按
        image.setOnLongClickListener {
            onLongClickListener()
            return@setOnLongClickListener false
        }

        //移动
        imageDragHelper.startDrag(image, holder, onDragListener)
    }


    //设置viewpager 是否可以滑动
    private fun setViewPagerEnable(isUserInputEnabled: Boolean) {
        larger_viewpager.isUserInputEnabled = isUserInputEnabled //true:滑动，false：禁止滑动
    }


    //进入动画
    private fun enterAnim(holder: RecyclerView.ViewHolder) {
        //防止viewpager 滑动时候再次触发
        if (animFinish) {
            return
        }
        animFinish = true
        //小图片---->  大图的动画
        val image = holder.itemView.findViewById<PhotoView>(getPhotoViewId())
        AnimEnterHelper.start(
            getPhotoViewId(),
            setDuration(),
            image,
            getImageArrayList()[mCurrentIndex],
            holder,
            enterAnimListener,
            afterTransitionListener
        )
        //背景颜色变化
        AnimBgEnterHelper.start(parentView, 0f, setDuration())
    }

    //退出的动画
    private fun exitAnim(start: Float, holder: RecyclerView.ViewHolder) {
        //大图片---->  小图的动画
        val image = holder.itemView.findViewById<PhotoView>(getPhotoViewId())
        AnimExitHelper.start(
            getPhotoViewId(),
            setDuration(),
            image,
            getImageArrayList()[mCurrentIndex],
            holder,
            exitAnimListener,
            afterTransitionListener
        )
        //背景颜色变化
        AnimBgExitHelper.start(parentView, start, setDuration())
    }


    //是否可以方法缩小 移动过程中不可以方法缩小
    private fun setZoomable(image: PhotoView, isZoomable: Boolean) {
        image.setCustomZoomable(isZoomable)
    }


    //点击返回
    override fun onBackPressed() {
        if (imageDragHelper.isAnimIng) {
            return
        }
        val holder = viewHolderMap[mCurrentIndex] ?: return
        exitAnim(1.0f, holder)
    }

    private fun log(info: String) {
        Log.i(TAG, info)
    }

    //==============================================================================================
    // 对外的方法
    //==============================================================================================


    //长按的事件
    open fun onLongClickListener() {}

    //单次点击事件
    open fun onSingleClickListener() {

    }

    //设置持续时间
    open fun setDuration(): Long {
        return 200
    }

    //默认拖动时候的阻尼系数   [0.0f----1.0f] 越小越难滑动
    open fun setDamping(): Float {
        return 1.0f
    }


    //设置下拉的参数 [0.0f----1.0f] 越小越容易退出
    open fun setFraction(): Float {
        return 0.5f
    }

    //==============================================================================================
    // 对外的抽象方法
    //==============================================================================================

    //默认的布局
    abstract fun getItemLayout(): Int

    //获取 PhotoViewId
    abstract fun getPhotoViewId(): Int

    //数据源
    abstract fun getData(): ArrayList<T>?

    //获取所有的图片信息
    abstract fun getImageArrayList(): ArrayList<ImageView>

    //当前的图片index
    abstract fun getIndex(): Int

    //加载图片
    abstract fun itemBindViewHolder(isLoadFull: Boolean, itemView: View, position: Int, data: T?)


}
