package com.starot.larger.fragment

import android.os.Bundle
import android.view.*
import android.widget.ImageView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.starot.larger.Larger
import com.starot.larger.anim.impl.AnimListener
import com.starot.larger.config.LargerConfig
import com.starot.larger.guest.GuestAgent
import com.starot.larger.guest.impl.OnGuestListener
import com.starot.larger.impl.OnLargerListener
import com.starot.larger.impl.OnLargerType
import com.starot.larger.utils.LogUtils


abstract class BaseLargerFragment<T : OnLargerType> : Fragment(),
    OnGuestListener,
    AnimListener,
    OnLargerListener {

    companion object {
        const val KEY_FRAGMENT_PARAMS = "com.allens.larger.fragment.config"
        const val KEY_FRAGMENT_PARAMS_POS = "com.allens.larger.fragment.config.pos"
    }

    private lateinit var fragmentView: View


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentView = inflater.inflate(getLayoutId(), container, false)
        return fragmentView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //手势监听
        GuestAgent().setGuestView(fragmentView, this)

        //获取数据源
        val data = arguments?.getParcelable<T>(KEY_FRAGMENT_PARAMS)
        if (data == null) {
            LogUtils.i(" fragment data is null")
            return
        }
        val position = arguments?.getInt(KEY_FRAGMENT_PARAMS_POS)
        if (position == null) {
            LogUtils.i(" fragment position is null")
            return
        }

        val mView = view.findViewById<View>(getFullViewId())

        if (mView == null) {
            LogUtils.i(" fragment view is null")
            return
        }
        loadBeforeAnimStart(Larger.largerConfig, data, mView, view)

        //执行动画
        enterAnimStart(view, getDuration(), mView, getThumbnailView(position))
    }






    //==============================================================================================
    // 抽象方法
    //==============================================================================================


    /***
     * [config] 使用配置中的加载器
     * [data] 数据
     * [fullView] 通过FUllId 获取到的 view
     * [view] 根视图
     * @Des 开始动画 之前做一些事情
     */
    abstract fun loadBeforeAnimStart(config: LargerConfig?, data: T, fullView: View, view: View)

    //实例化
    open fun newInstance(data: T, position: Int): BaseLargerFragment<T> {
        val args = Bundle()
        args.putParcelable(KEY_FRAGMENT_PARAMS, data)
        args.putInt(KEY_FRAGMENT_PARAMS_POS, position)
        arguments = args
        return this
    }

    //单点击手势
    override fun onSingleTap() {
    }

    //双击手势
    override fun onDoubleTap() {
    }

    //动画开始
    override fun onAnimatorStart() {
    }

    //动画结束
    override fun onAnimatorEnd() {
    }

    //动画取消
    override fun onAnimatorCancel() {
    }




}