package com.starot.larger.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.starot.larger.anim.impl.AnimListener
import com.starot.larger.enums.AnimStatus
import com.starot.larger.enums.AnimType
import com.starot.larger.impl.OnLargerListener
import com.starot.larger.impl.OnLargerType
import com.starot.larger.status.LargerStatus
import com.starot.larger.utils.LogUtils


abstract class BaseLargerFragment<T : OnLargerType> : Fragment(),
    AnimListener,
    OnLargerListener {

    companion object {
        const val KEY_FRAGMENT_PARAMS = "com.allens.larger.fragment.config"
        const val KEY_FRAGMENT_PARAMS_POS = "com.allens.larger.fragment.config.pos"
    }

    lateinit var fragmentView: View
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
            onDoBefore(data, fullView, getThumbnailView(position), position, view)
            return
        }
        LargerStatus.isLoad = true
        //执行动画
        enterAnimStart(view, getDuration(), fullView, getThumbnailView(position))
    }


    //==============================================================================================
    // 抽象方法
    //==============================================================================================


    //动画开始以前做啥事情
    abstract fun onDoBefore(
        data: T?,
        fullView: View?,
        thumbnailView: View?,
        position: Int,
        view: View
    )

    //动画结束以后做啥事情
    abstract fun onDoAfter(
        data: T?,
        fullView: View?,
        thumbnailView: View?,
        position: Int,
        view: View
    )

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

    //    override fun onLongPress() {
//        LogUtils.i("onLongPress")
//    }
//
//
//    //双击手势
//    override fun onDoubleTap() {
//        LogUtils.i("双击手势")
//        checkIsAnimIng()
//    }
//
//    //单点击手势
//    override fun onSingleTap() {
//        LogUtils.i("单点击手势")
//        if (checkIsAnimIng()) return
//        exitAnimStart(fragmentView, getDuration(), fullView, getThumbnailView(position))
//    }

    //
//    override fun onTranslate(x: Float, y: Float) {
//        LogUtils.i("onTranslate x $x y $y")
//        if (checkIsAnimIng()) return
//        fullView?.translationX = x
//        fullView?.translationY = y
//    }
//
//    override fun onScaleStart() {
//        LogUtils.i("onScaleStart")
//        if (checkIsAnimIng()) return
//    }
//
//    override fun onScaleEnd(scale: Float) {
//        LogUtils.i("onScaleEnd")
//        if (checkIsAnimIng()) return
//
//        if (scale < 1.0f) {
//            LogUtils.i("现在是缩小的状态  动画改成1.0 比例")
//        }
//
//    }
//
//    //缩放手势
//    override fun onScale(scale: Float, focusX: Float, focusY: Float): Boolean {
//        LogUtils.i("缩放手势 fullView scale $scale focusX $focusX focusY $focusY")
//        if (checkIsAnimIng()) return false
//        //当前的伸缩值*之前的伸缩值 保持连续性
////        if (scale > getMaxScale() || scale < getMinScale()) {
////            return false
////        }
//        fullView?.pivotX = focusX
//        fullView?.pivotY = focusY
//        fullView?.scaleY = scale
//        fullView?.scaleX = scale
//        return true
//
//    }
//
//    //拖动
//    override fun onDrag(x: Float, y: Float): Boolean {
//        LogUtils.i("拖动 X $x y $y")
//        if (checkIsAnimIng()) return false
//        startDrag(fragmentView, fullView, x, y)
//        return true
//    }
//
//    override fun onDragStart() {
//        LogUtils.i("onDragStart")
//        if (checkIsAnimIng()) return
//    }
//
//    override fun onDragEnd() {
//        LogUtils.i("onDragEnd")
//        if (checkIsAnimIng()) return
//        endDrag(fullView)
//    }


    //drag 以后推出
    override fun onDragExit(scale: Float, fullView: View) {
        LogUtils.i("drag 以后推出")
        if (isAnimIng()) return
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
        if (isAnimIng()) return
        dragResumeAnimStart(
            scale,
            fragmentView,
            getDuration(),
            fullView,
            getThumbnailView(position)
        )
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
            AnimType.ENTER, AnimType.DRAG_RESUME -> {
                LargerStatus.status.postValue(AnimStatus.ENTER_END)
                onDoAfter(data, fullView, getThumbnailView(position), position, fragmentView)
            }
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
    fun isAnimIng(): Boolean {
        if (
            LargerStatus.status.value == AnimStatus.ENTER_START
            || LargerStatus.status.value == AnimStatus.EXIT_START
//            || LargerStatus.status.value == AnimStatus.DRAG_START
        ) {
            return true
        }
        return false
    }

}