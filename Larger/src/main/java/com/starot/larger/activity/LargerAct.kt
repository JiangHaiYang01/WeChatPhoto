package com.starot.larger.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.starot.larger.R
import com.starot.larger.adapter.ViewPagerAdapter
import com.starot.larger.anim.AnimBgEnterHelper
import com.starot.larger.anim.AnimBgExitHelper
import com.starot.larger.anim.AnimEnterHelper
import com.starot.larger.anim.AnimExitHelper
import com.starot.larger.impl.OnAfterTransitionListener
import com.starot.larger.impl.OnAnimatorListener
import com.starot.larger.view.image.PhotoView
import kotlinx.android.synthetic.main.activity_larger_base.*


abstract class LargerAct<T> : AppCompatActivity() {

    companion object {
        const val TAG = "LargerActTAG"
    }


    //默认显示时间
    private var defDuration: Long = 300

    //默认拖动时候的阻尼系数   [1.0----4.0f]
    private var damping: Float = 1.0f

//    //是否发生过移动
//    private var isDrag = false
//
//    //图片缩放比例
//    private var currentOriginalScale = 0f

    //是否正在动画
    private var isAnimIng = false

    //进入的动画播放完成了
    private var animFinish = false

    //进入动画效果
    private val enterAnimListener = object : OnAnimatorListener {
        override fun onAnimatorStart() {
            Log.i(TAG, "进入的动画 start")
            setViewPagerEnable(false)
            isAnimIng = true
        }

        override fun onAnimatorEnd() {
            Log.i(TAG, "进入的动画 end")
            setViewPagerEnable(true)
            isAnimIng = false
        }
    }

    //退出的动画效果
    private val exitAnimListener = object : OnAnimatorListener {
        override fun onAnimatorStart() {
            setViewPagerEnable(false)
            Log.i(TAG, "退出的动画 start")
            isAnimIng = true
        }

        override fun onAnimatorEnd() {
            Log.i(TAG, "退出的动画 end")
            isAnimIng = false
            setViewPagerEnable(true)
            finish()
            overridePendingTransition(0, 0)
        }
    }

    //动画执行以后
    private val afterTransitionListener = object : OnAfterTransitionListener {
        override fun afterTransitionLoad(holder: RecyclerView.ViewHolder) {
            itemBindViewHolder(holder.itemView, mCurrentIndex, getData()?.get(mCurrentIndex))
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
                    val itemView = holder.itemView
                    //通用方法
                    item(holder, position, data?.get(position))
                    //用户自己处理加载逻辑
                    itemBindViewHolder(itemView, position, data?.get(position))
                    //进入动画
                    enterAnim(holder)
                }
            })
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
            exitAnim(holder)
        }

        //长按
        image.setOnLongClickListener {
            onLongClickListener()
            return@setOnLongClickListener false
        }
    }


//    //是否可以方法缩小 移动过程中不可以方法缩小
//    private fun setZoomable(isZoomable: Boolean) {
////        image.setCustomZoomable(isZoomable)
//    }


    //设置viewpager 是否可以滑动
    private fun setViewPagerEnable(isUserInputEnabled: Boolean) {
        larger_viewpager.isUserInputEnabled = isUserInputEnabled //true:滑动，false：禁止滑动
    }


    //进入动画
    private fun enterAnim(holder: ViewPagerAdapter.PhotoViewHolder) {
        //防止viewpager 滑动时候再次触发
        if (animFinish) {
            return
        }
        animFinish = true
        //小图片---->  大图的动画
        Log.i(TAG, "小图片---->  大图的动画")
        val image = holder.itemView.findViewById<PhotoView>(getPhotoViewId())
        AnimEnterHelper.start(
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
    private fun exitAnim(holder: ViewPagerAdapter.PhotoViewHolder) {
        //大图片---->  小图的动画
        Log.i(TAG, "大图片---->  小图的动画 $holder")
        val image = holder.itemView.findViewById<PhotoView>(getPhotoViewId())
        AnimExitHelper.start(
            setDuration(),
            image,
            getImageArrayList()[mCurrentIndex],
            holder,
            exitAnimListener,
            afterTransitionListener
        )
        //背景颜色变化
        AnimBgExitHelper.start(parentView, 1.0f, setDuration())
    }

    //==============================================================================================
    // 对外的方法
    //==============================================================================================


    //长按的事件
    open fun onLongClickListener() {}

    //单次点击事件
    open fun onSingleClickListener() {

    }

    //设置显示时间
    open fun setDuration(): Long {
        return defDuration
    }

    //拖动时候的阻尼系数
    open fun setDamping(): Float {
        return damping
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
    abstract fun itemBindViewHolder(itemView: View, position: Int, data: T?)


}
