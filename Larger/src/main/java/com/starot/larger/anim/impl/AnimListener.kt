package com.starot.larger.anim.impl

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.starot.larger.Larger
import com.starot.larger.anim.AnimBgHelper
import com.starot.larger.anim.AnimEnterHelper
import com.starot.larger.anim.AnimExitHelper
import com.starot.larger.enums.AnimType
import com.starot.larger.utils.LogUtils

//动画的逻辑
interface AnimListener : OnAnimatorListener {


    fun enterAnimStart(
        parentView: View,
        duration: Long,
        fullView: View?,
        thumbnailView: View?
    ) {
        LogUtils.i("入场动画 start")
        if(fullView == null){
            return
        }
        AnimEnterHelper.start(
            AnimType.ENTER,
            duration,
            fullView,
            thumbnailView,
            this,
        )
        //背景颜色变化
        AnimBgHelper.enter(parentView, 0f, duration)
    }

    fun exitAnimStart(
        parentView: View,
        duration: Long,
        fullView: View?,
        thumbnailView: View?,
    ) {
        LogUtils.i("退场动画 start")
        if(fullView == null){
            return
        }
        AnimExitHelper.start(
            AnimType.EXIT,
            duration,
            fullView,
            thumbnailView,
            this
        )
        //背景颜色变化
        AnimBgHelper.exit(parentView, 1.0f, duration)
    }

}