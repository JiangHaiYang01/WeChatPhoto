package com.example.largerloadvideo

import android.content.Context
import android.view.View
import android.widget.ImageView
import androidx.lifecycle.MutableLiveData
import cn.jzvd.JZUtils
import cn.jzvd.Jzvd
import cn.jzvd.JzvdStd
import com.starot.larger.bean.LargerBean
import com.starot.larger.view.image.OnLargerDragListener
import com.starot.larger.impl.OnVideoLoadListener
import com.starot.larger.utils.LogUtils


//视屏加载器
class LargerVideoLoad(private val context: Context) : OnVideoLoadListener {


    override fun getPoster(view: View): ImageView {
        val video = view.findViewById<JzvdStd>(getVideoViewId())
        return video.posterImageView
    }


    override fun loadVideo(data: LargerBean, view: View, listener: OnLargerDragListener) {
        LogUtils.i("loadVideo")
        val video = view.findViewById<MyVideoView>(getVideoViewId())?:return
        video.setDragListener(object : OnLargerDragListener {
            override fun onDrag(x: Float, y: Float) {
                listener.onDrag(x, y = y)
            }

            override fun onDragEnd() {
                listener.onDragEnd()
                Jzvd.goOnPlayOnResume()
            }

            override fun onDragPrepare(dx: Float, dy: Float): Boolean {
                return listener.onDragPrepare(dx, dy)
            }

            override fun onDragStart() {
                listener.onDragStart()
                Jzvd.goOnPlayOnPause()
                video.setButtonProgressStatus(false)
            }
        })
        video.setUp(data.fullUrl, "", Jzvd.SCREEN_NORMAL)
        video.startVideoAfterPreloading()
    }


    override fun onRelease() {
        LogUtils.i("onRelease")
        Jzvd.releaseAllVideos()
    }

    override fun onPause() {
        LogUtils.i("onPause")
        JZUtils.clearSavedProgress(context.applicationContext, null)
        Jzvd.goOnPlayOnPause()
    }

    override fun onResume() {
        LogUtils.i("onResume")
        Jzvd.goOnPlayOnResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        Jzvd.releaseAllVideos()
    }


    override fun getVideoViewId(): Int {
        return R.id.audio_video
    }


    override fun getVideoLayoutId(): Int {
        return R.layout.item_larger_video
    }

}