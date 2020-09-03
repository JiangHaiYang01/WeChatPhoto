package com.starot.larger.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.starot.larger.anim.impl.AnimListener
import com.starot.larger.enums.AnimStatus
import com.starot.larger.enums.AnimType
import com.starot.larger.guest.GuestAgent
import com.starot.larger.guest.impl.OnGuestListener
import com.starot.larger.impl.OnLargerListener
import com.starot.larger.impl.OnLargerType
import com.starot.larger.status.LargerStatus
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
    private var data: T? = null
    private var fullView: View? = null
    private var position: Int = -1

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
        data = arguments?.getParcelable<T>(KEY_FRAGMENT_PARAMS)
        //下标
        val pos = arguments?.getInt(KEY_FRAGMENT_PARAMS_POS)
        if (pos == null) {
            this.position = -1
        } else {
            this.position = pos
        }
        //加载的view
        fullView = view.findViewById(getFullViewId())
        if (LargerStatus.isLoad) {
            LogUtils.i("判断已经执行过了不在触发")
            onLoad(data, fullView, position)
            return
        }
        LargerStatus.isLoad = true
        //执行动画
        enterAnimStart(view, getDuration(), fullView, getThumbnailView(position))
    }


    //==============================================================================================
    // 抽象方法
    //==============================================================================================


    //处理原本应该干的事情
    abstract fun onLoad(data: T?, fullView: View?, position: Int)

    //数据
    fun getData(): T? {
        return data
    }


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
        LogUtils.i("单点击手势")
        if (isAnimIng()) {
            LogUtils.i("正在执行动画 点击无效")
            return
        }
        exitAnimStart(fragmentView, getDuration(), fullView, getThumbnailView(position))
    }

    //缩放手势
    override fun onScale(scaleFactor: Float, focusX: Float, focusY: Float) {
        LogUtils.i("缩放手势 fullView scaleFactor $scaleFactor focusX $focusX focusY $focusY")
        if (isAnimIng()) {
            LogUtils.i("正在执行动画 点击无效")
            return
        }
//        fullView?.scaleY = scaleFactor
//        fullView?.scaleX = scaleFactor
    }

    //拖动
    override fun onDrag(x: Float, y: Float) {
//        LogUtils.i("拖动 X $x y $y")
        if (isAnimIng()) {
            LogUtils.i("正在执行动画 点击无效")
            return
        }
        startDrag(fragmentView, fullView, x, y)
    }

    override fun onDragStart() {
        LogUtils.i("onDragStart")
    }

    override fun onDragEnd() {
        LogUtils.i("onDragEnd")
        if (isAnimIng()) {
            LogUtils.i("正在执行动画 点击无效")
            return
        }
        endDrag(fullView)
    }


    //drag 以后推出
    override fun onDragExit(scale: Float, fullView: View) {
        LogUtils.i("drag 以后推出")
        if (isAnimIng()) {
            LogUtils.i("正在执行动画 点击无效")
            return
        }
        exitAnimStart(
            AnimType.DRAG_EXIT,
            scale,
            fragmentView,
            getDuration(),
            fullView,
            getThumbnailView(position)
        )
    }

    //drag 以后恢复
    override fun onDragResume(scale: Float, fullView: View) {
        LogUtils.i("drag 以后恢复")
        if (isAnimIng()) {
            LogUtils.i("正在执行动画 点击无效")
            return
        }
        dragResumeAnimStart(
            scale,
            fragmentView,
            getDuration(),
            fullView,
            getThumbnailView(position)
        )
    }

    //双击手势
    override fun onDoubleTap() {
        LogUtils.i("双击手势")
        if (isAnimIng()) {
            LogUtils.i("正在执行动画 点击无效")
            return
        }
    }

    //动画开始
    override fun onAnimatorStart(type: AnimType) {
        when (type) {
            AnimType.ENTER, AnimType.DRAG_RESUME -> LargerStatus.status.postValue(AnimStatus.ENTER_START)
            AnimType.EXIT, AnimType.DRAG_EXIT -> LargerStatus.status.postValue(AnimStatus.EXIT_START)
        }
    }

    //动画结束
    override fun onAnimatorEnd(type: AnimType) {
        when (type) {
            AnimType.ENTER, AnimType.DRAG_RESUME -> LargerStatus.status.postValue(AnimStatus.ENTER_END)
            AnimType.EXIT, AnimType.DRAG_EXIT -> LargerStatus.status.postValue(AnimStatus.EXIT_END)
        }
    }

    //动画取消
    override fun onAnimatorCancel(type: AnimType) {
        when (type) {
            AnimType.ENTER, AnimType.DRAG_RESUME -> LargerStatus.status.postValue(AnimStatus.ENTER_END)
            AnimType.EXIT, AnimType.DRAG_EXIT -> LargerStatus.status.postValue(AnimStatus.EXIT_END)
        }
    }


    //是否正在动画
    private fun isAnimIng(): Boolean {
        if (LargerStatus.status.value == AnimStatus.ENTER_START || LargerStatus.status.value == AnimStatus.EXIT_START) {
            return true
        }
        return false
    }

}