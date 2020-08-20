package com.starot.larger.act

import android.os.Bundle
import android.transition.Fade
import android.transition.Transition
import android.transition.TransitionManager
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.starot.larger.R
import com.starot.larger.adapter.ViewPagerAdapter
import com.starot.larger.impl.AnimListener
import com.starot.larger.utils.PageChange
import kotlinx.android.synthetic.main.activity_larger_base.*


abstract class LargerAct<T> : AppCompatActivity(),
    ViewPagerAdapter.OnBindViewHolderListener,
    AnimListener,
    PageChange.PageChangeListener {


    //根视图
    private lateinit var parentView: View

    //数据源
    private var data: List<T>? = null

    //当前的index
    private var mCurrentIndex = 0

    //动画时间
    private var duration: Long = 300

    //缩略图
    private var thumbnailView: ImageView? = null

    //进入的动画加载完成
    private var isLoadEnter = false


    override fun onCreate(savedInstanceState: Bundle?) {
        overridePendingTransition(0, 0)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_larger_base)


        //开始之前
        beforeCreate()
        duration = getDuration()


        //数据源
        data = getData()
        //根视图
        parentView = larger_parent
        //当前的下标
        mCurrentIndex = getIndex()
        //获取当前列表的imageView
        thumbnailView = getThumbnailView(mCurrentIndex)
        //设置适配器
        larger_viewpager.adapter = ViewPagerAdapter(data, getItemLayout(), this)
        //不需要平滑过渡了
        larger_viewpager.setCurrentItem(mCurrentIndex, false)
        //viewpager 滑动 index 更改
        PageChange().register(viewPager2 = larger_viewpager, listener = this)
    }


    //设置viewpager 是否可以滑动
    private fun setViewPagerEnable(isUserInputEnabled: Boolean) {
        larger_viewpager.isUserInputEnabled = isUserInputEnabled //true:滑动，false：禁止滑动
    }


    override fun onBindViewHolder(holder: ViewPagerAdapter.PhotoViewHolder, position: Int) {
        //通用方法
        itemCommand(holder, position, data?.get(position))
        //用户自己处理加载逻辑
        itemBindViewHolder(false, holder.itemView, position, data?.get(position))
        //首次进入加载完成之后不再展示动画
        if (isLoadEnter) {
            return
        }
        val fullImageView = holder.itemView.findViewById<ImageView>(getFullViewId())
        enterAnimStart(
            parentView,
            duration,
            fullImageView,
            thumbnailView,
            holder
        )
    }

    private fun itemCommand(holder: ViewPagerAdapter.PhotoViewHolder, position: Int, get: T?) {
        val image = holder.itemView.findViewById<ImageView>(getFullViewId())
        //单点击退出
        image.setOnClickListener {
            val fullImageView = holder.itemView.findViewById<ImageView>(getFullViewId())
            thumbnailView = getThumbnailView(mCurrentIndex)
            exitAnimStart(
                parentView,
                duration,
                fullImageView,
                thumbnailView,
                holder
            )
        }
    }

    //进入动画结束
    override fun onEnterAnimEnd() {
        setViewPagerEnable(true)
    }

    //进入动画开始
    override fun onEnterAnimStart() {
        isLoadEnter = true
        setViewPagerEnable(false)
    }


    //退出动画结束
    override fun onExitAnimEnd() {
        finish()
        overridePendingTransition(0, 0)
    }

    //退出动画开始
    override fun onExitAnimStart() {
        setViewPagerEnable(false)
    }

    //viewpager 滑动监听
    override fun onPageChange(pos: Int) {
        mCurrentIndex = pos
        getThumbnailView(pos)
    }

    override fun onReLoadFullImage(holder: RecyclerView.ViewHolder) {
        itemBindViewHolder(true, holder.itemView, mCurrentIndex, data?.get(mCurrentIndex))
    }

    //点击返回
//    override fun onBackPressed() {
//    }


    //onCreate 第一件时间
    abstract fun beforeCreate()

    //动画时长
    abstract fun getDuration(): Long

    //大图的id
    abstract fun getFullViewId(): Int

    //当前的图片index
    abstract fun getIndex(): Int

    //获取缩略图
    abstract fun getThumbnailView(position: Int): ImageView?


    //数据源
    abstract fun getData(): List<T>?

    //默认的布局
    abstract fun getItemLayout(): Int

    //加载图片 对外是可自定义处理
    abstract fun itemBindViewHolder(isLoadFull: Boolean, itemView: View, position: Int, data: T?)

}