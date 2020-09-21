package com.starot.larger.impl

import android.view.View
import android.widget.ImageView
import com.starot.larger.bean.LargerBean
import com.starot.larger.fragment.VideoFg
import com.starot.larger.image.OnLargerDragListener

//加载图片接口
interface OnVideoLoadListener : OnLoadProgressPrepareListener,
    OnVideoViewIdListener, OnLifecycleListener {

    //返回预览图
    fun getPoster(view: View): ImageView

    //加载视屏
    fun loadVideo(data: LargerBean, view: View, listener: OnLargerDragListener)


    //清理资源
    fun onRelease()

}


interface OnVideoViewIdListener {
    //返回视屏的id
    fun getVideoViewId(): Int

    //返回布局id
    fun getVideoLayoutId(): Int


}