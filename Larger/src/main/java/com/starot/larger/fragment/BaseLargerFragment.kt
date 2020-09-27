package com.starot.larger.fragment

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.starot.larger.anim.impl.AnimListener
import com.starot.larger.enums.AnimStatus
import com.starot.larger.enums.AnimType
import com.starot.larger.enums.BackEnum
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
    var position: Int = -1
    val handler = Handler(Looper.getMainLooper())

    //碎片显示的状态
    private var fragmentVisitStatus = MutableLiveData<Boolean>().apply {
        value = false
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //点击返回事件
        LargerStatus.back.observe(context as AppCompatActivity, {
            if (it == BackEnum.BACK_PREPARE) {
                if (isAnimIng()) {
                    LargerStatus.back.value = BackEnum.BACK_NOME
                    return@observe
                }
                exitAnimStart(fragmentView, getDuration(), fullView, getThumbnailView(position))
            }
        })
    }

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

        LogUtils.i("baseFragment viewCreate $pos")

        //监听viewpager2 滑动变化
        registerViewPagerChange(pos)

        //加载的view
        fullView = view.findViewById(getFullViewId())

        val isLoad = LargerStatus.isLoad.value
        if (isLoad != null && isLoad) {
            LogUtils.i("判断已经执行过了不在触发")
            fragmentView.setBackgroundColor(getBackGroundColor())
            onDoBefore(data, fullView, getThumbnailView(position), position, view)
            onAlreadyLoad(data, fullView, getThumbnailView(position), position, view, {

                //这里是否等到新的一页在触发 权限放给开发者自行处理
                if (isAutoLoadNextFragment()) {
                    handler.post {
                        onDoAfter(data, fullView, getThumbnailView(position), position, view)
                    }
                } else {
                    //这里需要处理一下，如果已经加载过了的 不触发动画，但是要等到 界面可见的时候才能出发下面的逻辑
                    fragmentVisitStatus.observe(this, {
                        if (it) {
                            LogUtils.i("可以触发 onDoAfter 方法")
                            handler.post {
                                onDoAfter(
                                    data,
                                    fullView,
                                    getThumbnailView(position),
                                    position,
                                    view
                                )
                            }
                        }
                    })
                }


            })
            return
        }
        LogUtils.i("首次加载进入----------------")
        onDoBefore(data, fullView, getThumbnailView(position), position, view)
        LargerStatus.isLoad.value = true
        //执行动画
        enterAnimStart(view, getDuration(), fullView, getThumbnailView(position))
    }

    private fun registerViewPagerChange(pos: Int?) {
        if (pos == null) {
            return
        }
        if (pos < 0) {
            return
        }
        LargerStatus.pos.observe(context as AppCompatActivity, Observer {
            LogUtils.i("baseFragment pos change:$it currentFragmentIndex:$pos")
            if (pos == it && fragmentVisitStatus.value == false) {
                LogUtils.i("baseFragment---> $pos: 显示")
                fragmentVisitStatus.value = true
            } else if (fragmentVisitStatus.value == true && pos != it) {
                LogUtils.i("baseFragment---> $pos: 隐藏")
                fragmentVisitStatus.value = false
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        //需要手动去remove
        LargerStatus.pos.removeObservers(context as AppCompatActivity)
    }


    //==============================================================================================
    // 抽象方法
    //==============================================================================================


    abstract fun onAlreadyLoad(
        data: T?,
        fullView: View?,
        thumbnailView: View?,
        position: Int,
        view: View,
        success: (T) -> Unit
    )

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
            AnimType.ENTER, AnimType.DRAG_RESUME -> LargerStatus.status.value =
                (AnimStatus.ENTER_START)
            AnimType.EXIT, AnimType.DRAG_EXIT -> LargerStatus.status.value = (AnimStatus.EXIT_START)
        }
    }

    //动画结束
    override fun onAnimatorEnd(type: AnimType) {
        when (type) {
            AnimType.ENTER, AnimType.DRAG_RESUME -> {
                LargerStatus.status.value = (AnimStatus.ENTER_END)
                onDoAfter(data, fullView, getThumbnailView(position), position, fragmentView)
            }
            AnimType.EXIT, AnimType.DRAG_EXIT -> LargerStatus.status.value = (AnimStatus.EXIT_END)
        }

    }

    //动画取消
    override fun onAnimatorCancel(type: AnimType) {
        when (type) {
            AnimType.ENTER, AnimType.DRAG_RESUME -> LargerStatus.status.value =
                (AnimStatus.ENTER_END)
            AnimType.EXIT, AnimType.DRAG_EXIT -> LargerStatus.status.value = (AnimStatus.EXIT_END)
        }
    }


    //是否正在动画
    fun isAnimIng(): Boolean {
        if (
            LargerStatus.status.value == AnimStatus.ENTER_START
            || LargerStatus.status.value == AnimStatus.EXIT_START
//            || LargerStatus.status.value == AnimStatus.SCALE_START
//            || LargerStatus.status.value == AnimStatus.DRAG_START
        ) {
            return true
        }
        return false
    }

//    override fun onResume() {
//        super.onResume()
//        view?.isFocusableInTouchMode = true
//        view?.requestFocus()
//        view?.setOnKeyListener(object : View.OnKeyListener {
//            override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
//                if (keyCode == KeyEvent.KEYCODE_BACK && event?.action == KeyEvent.ACTION_UP) {
//                    LogUtils.i("fragment back")
//                    exitAnimStart(fragmentView, getDuration(), fullView, getThumbnailView(position))
//                    return true
//                }
//                return false
//            }
//        })
//
//    }

}