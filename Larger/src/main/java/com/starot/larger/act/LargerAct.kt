package com.starot.larger.act

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.starot.larger.Larger
import com.starot.larger.R
import com.starot.larger.adapter.FgPageAdapter
import com.starot.larger.enums.AnimStatus
import com.starot.larger.enums.BackEnum
import com.starot.larger.impl.OnLargerConfigListener
import com.starot.larger.impl.OnLargerType
import com.starot.larger.status.LargerStatus
import com.starot.larger.utils.LogUtils
import com.starot.larger.utils.PageChange
import com.starot.larger.utils.StatusBarTools
import kotlinx.android.synthetic.main.activity_larger_base.*

abstract class LargerAct<T : OnLargerType> : AppCompatActivity(),
    PageChange.PageChangeListener,
    OnLargerConfigListener,
    FgPageAdapter.OnCreateFragmentListener<T> {

    //当前的index
    private var mCurrentIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        //取消动画
        overridePendingTransition(0, 0)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_larger_base)
        //沉寂式
        StatusBarTools.setStatusBar(this)

        //当前的index
        mCurrentIndex = getIndex()

        //添加到了lifecycle
        val videoLoad = Larger.largerConfig?.videoLoad
        val imageLoad = Larger.largerConfig?.imageLoad
        if (imageLoad != null) {
            lifecycle.addObserver(imageLoad)
        }
        if (videoLoad != null) {
            lifecycle.addObserver(videoLoad)
        }
        lifecycle.addObserver(LargerStatus)

        //设置适配器
        larger_viewpager.adapter =
            FgPageAdapter<T>(supportFragmentManager, lifecycle, getData(), this)
        //不需要平滑过渡了
        larger_viewpager.setCurrentItem(mCurrentIndex, false)
        //viewpager 滑动 index 更改
        PageChange().register(viewPager2 = larger_viewpager, listener = this)


        //竖着滑动
        larger_viewpager.orientation = getOrientation()


        //viewpager2 默认没有懒加载 这里给设置 1 提高滑动时候的体验
        larger_viewpager.offscreenPageLimit = 1

        //检查状态判断是否可以滑动viewpager
        LargerStatus.status.observe(this, {
            LogUtils.i("动画状态 $it")
            when (it) {
                AnimStatus.ENTER_START, AnimStatus.EXIT_START, AnimStatus.DRAG_START, AnimStatus.SCALE_START -> {
                    larger_viewpager.isUserInputEnabled = false //true:滑动，false：禁止滑动
                    LogUtils.i("viewPager--------禁止滑动")
                }
                AnimStatus.ENTER_END, AnimStatus.SCALE_END -> {
                    larger_viewpager.isUserInputEnabled = true //true:滑动，false：禁止滑动
                    LogUtils.i("viewPager-------滑动")
                }
                AnimStatus.EXIT_END -> {
                    LogUtils.i("viewPager-------滑动")
                    larger_viewpager.isUserInputEnabled = true //true:滑动，false：禁止滑动
                    finish()
                    overridePendingTransition(0, 0)
                }
            }
        })

    }

    //数据源
    private fun getData(): List<T>? {
        return Larger.largerConfig?.data as List<T>?
    }


    //当前的图片index
    private fun getIndex(): Int {
        return Larger.largerConfig?.position ?: 0
    }


    //viewpager 滑动监听
    override fun onPageChange(pos: Int) {
        mCurrentIndex = pos
        LogUtils.i("viewPager change:$pos")
        LargerStatus.pos.value = pos
    }


    override fun onDestroy() {
        super.onDestroy()
        //清理资源
        Larger.largerConfig = null
    }

    override fun onBackPressed() {
        if (LargerStatus.back.value == BackEnum.BACK_NOME) {
            LargerStatus.back.value = BackEnum.BACK_PREPARE
        }
    }


}