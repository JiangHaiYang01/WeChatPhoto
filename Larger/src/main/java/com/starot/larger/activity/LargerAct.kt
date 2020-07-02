package com.starot.larger.activity

import android.animation.Animator
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.starot.larger.R
import com.starot.larger.adapter.ViewPagerAdapter
import com.starot.larger.anim.LargerAnim
import com.starot.larger.bean.ImageInfo
import com.starot.larger.tools.ImageTool
import kotlinx.android.synthetic.main.activity_larger_base.*
import java.util.*


abstract class LargerAct<T> : AppCompatActivity(), Animator.AnimatorListener {


    //默认显示时间
    private var defDuration: Long = 300

    //当前的index
    private var mCurrentIndex = 0

    companion object {
        const val IMAGE = "images"
        const val INDEX = "index"
        const val ORIGINAL = "ORIGINAL"
    }

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
                    holder: ViewPagerAdapter.MyViewHolder,
                    position: Int
                ) {
                    val itemView = holder.itemView
                    if (data == null) {
                        item(itemView, position, null)
                    } else {
                        item(itemView, position, data[position])
                    }
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


    override fun onResume() {
        super.onResume()
        //从小图片 到 大图片的动画
        val info = getImageInfo()[getCurrentItemIndex()]
        val originalScale =
            ImageTool.getCurrentPicOriginalScale(this, info)

        LargerAnim.startEnter(
            this,
            larger_parent,
            larger_viewpager,
            originalScale,
            info,
            setDuration(),
            null
        )
    }


    abstract fun getItemLayout(): Int

    abstract fun item(itemView: View, position: Int, data: T?)

    abstract fun getData(): List<T>?

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

    //设置显示时间
    open fun setDuration(): Long {
        return defDuration
    }


    override fun onBackPressed() {
        //点击返回无效
    }

    //退出动画
    fun exitAnim() {
        val info = getImageInfo()[getCurrentItemIndex()]
        val originalScale =
            ImageTool.getCurrentPicOriginalScale(this, info)
        LargerAnim.startExit(
            this,
            larger_parent,
            larger_viewpager,
            originalScale,
            info,
            setDuration(),
            this
        )
    }

    override fun onAnimationRepeat(p0: Animator?) {

    }

    override fun onAnimationEnd(p0: Animator?) {
        finish()
        overridePendingTransition(0, 0)
    }

    override fun onAnimationCancel(p0: Animator?) {
    }

    override fun onAnimationStart(p0: Animator?) {
    }


}
