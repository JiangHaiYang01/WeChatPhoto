package com.starot.larger.act

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.starot.larger.R
import com.starot.larger.adapter.ViewPagerAdapter
import com.starot.larger.impl.AnimListener
import com.starot.larger.impl.OnAfterTransitionListener
import com.starot.larger.impl.OnAnimatorListener
import com.starot.larger.utils.PageChange
import kotlinx.android.synthetic.main.activity_larger_base.*

abstract class LargerAct<T> : AppCompatActivity(),
    ViewPagerAdapter.OnBindViewHolderListener,
    AnimListener,
    PageChange.PageChangeListener {


    companion object{
        const val TAG = "allens_tag"
    }

    //根视图
    private lateinit var parentView: View

    //数据源
    private var data: List<T>? = null

    //当前的index
     var mCurrentIndex = 0

    //动画时间
    private var duration: Long = 300

    //缩略图
    private lateinit var thumbnailView: ImageView


    //进入动画效果
    private val enterAnimListener = object : OnAnimatorListener {
        override fun onAnimatorStart() {
//            animStart()
        }

        override fun onAnimatorEnd() {
//            setViewPagerEnable(true)
//            imageDragHelper.isAnimIng = false
//            animEnd()
        }
    }

    //动画执行以后
    private val afterTransitionListener = object : OnAfterTransitionListener {
        override fun afterTransitionLoad(isLoadFull: Boolean, holder: RecyclerView.ViewHolder) {
            itemBindViewHolder(
                isLoadFull,
                holder.itemView,
                mCurrentIndex,
                getData()?.get(mCurrentIndex)
            )
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        overridePendingTransition(0, 0)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_larger_base)


        //开始之前
        beforeCreate()
        duration = getDuration()
        thumbnailView = getThumbnailView()

        //数据源
        data = getData()
        //根视图
        parentView = larger_parent
        //当前的下标
        mCurrentIndex = getIndex()
        //设置适配器
        larger_viewpager.adapter = ViewPagerAdapter(data, getItemLayout(), this)
        //不需要平滑过渡了
        larger_viewpager.setCurrentItem(mCurrentIndex, false)
        //viewpager 滑动 index 更改
        PageChange().register(viewPager2 = larger_viewpager, listener = this)
    }


    override fun onBindViewHolder(holder: ViewPagerAdapter.PhotoViewHolder, position: Int) {
        //用户自己处理加载逻辑
        itemBindViewHolder(false, holder.itemView, position, data?.get(position))

        val fullImageView = holder.itemView.findViewById<ImageView>(getFullViewId())
        enterAnimStart(
            parentView,
            duration,
            fullImageView,
            thumbnailView,
            holder,
            enterAnimListener,
            afterTransitionListener
        )
    }

    //viewpager 滑动监听
    override fun onPageChange(pos: Int) {
        mCurrentIndex = pos
    }


    //onCreate 第一件时间
    abstract fun beforeCreate()

    //动画时长
    abstract fun getDuration(): Long

    //大图的id
    abstract fun getFullViewId(): Int

    //当前的图片index
    abstract fun getIndex(): Int

    //获取缩略图
    abstract fun getThumbnailView(): ImageView

    //数据源
    abstract fun getData(): List<T>?

    //默认的布局
    abstract fun getItemLayout(): Int

    //加载图片 对外是可自定义处理
    abstract fun itemBindViewHolder(isLoadFull: Boolean, itemView: View, position: Int, data: T?)

}