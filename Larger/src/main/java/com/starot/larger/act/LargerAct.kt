package com.starot.larger.act

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.starot.larger.Larger
import com.starot.larger.R
import com.starot.larger.adapter.FgPageAdapter
import com.starot.larger.utils.PageChange
import kotlinx.android.synthetic.main.activity_larger_base.*

abstract class LargerAct<T> : AppCompatActivity(), PageChange.PageChangeListener,
    FgPageAdapter.OnCreateFragmentListener<T> {

    //当前的index
    private var mCurrentIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        //取消动画
        overridePendingTransition(0, 0)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_larger_base)

        //当前的index
        mCurrentIndex = getIndex()

        //设置适配器
        larger_viewpager.adapter =
            FgPageAdapter<T>(supportFragmentManager, lifecycle, getData(), this)
        //不需要平滑过渡了
        larger_viewpager.setCurrentItem(mCurrentIndex, false)
        //viewpager 滑动 index 更改
        PageChange().register(viewPager2 = larger_viewpager, listener = this)

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
    }


}