package com.starot.larger.impl

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.lifecycle.MutableLiveData
import com.starot.larger.anim.impl.OnAnimatorListener
import com.starot.larger.bean.LargerBean
import com.starot.larger.image.OnLargerDragListener

//加载图片接口
interface OnVideoLoadListener : OnLoadProgressPrepareListener,
    OnVideoViewIdListener {

    //返回预览图
    fun getPoster(view: View): ImageView

    //加载视屏
    fun loadVideo(data: LargerBean, view: View)

    fun dragListener(view: View,listener: OnLargerDragListener)
}


interface OnVideoViewIdListener {
    //返回视屏的id
    fun getVideoViewId(): Int

    //返回布局id
    fun getVideoLayoutId(): Int

    //返回转换时候的imageViewId
//    fun getImageViewId(): Int


}