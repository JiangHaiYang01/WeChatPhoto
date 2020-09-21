package com.example.largerloadvideo

import android.content.Context
import android.view.View
import android.widget.ImageView
import androidx.lifecycle.MutableLiveData
import cn.jzvd.JZUtils
import cn.jzvd.Jzvd
import cn.jzvd.JzvdStd
import com.starot.larger.bean.LargerBean
import com.starot.larger.fragment.VideoFg
import com.starot.larger.image.OnLargerDragListener
import com.starot.larger.impl.OnVideoLoadListener
import com.starot.larger.utils.LogUtils


//视屏加载器
class LargerVideoLoad(private val context: Context) : OnVideoLoadListener {


//    private var progressLiveData: MutableLiveData<Int>? = null
//    private var progressViewLiveData: MutableLiveData<Boolean>? = null

    override fun getPoster(view: View): ImageView {
        val video = view.findViewById<JzvdStd>(getVideoViewId())
        return video.posterImageView
    }


    override fun loadVideo(data: LargerBean, view: View, listener: OnLargerDragListener) {
        LogUtils.i("loadVideo")
        val video = view.findViewById<MyVideoView>(getVideoViewId())
        video.setDragListener(object : OnLargerDragListener {
            override fun onDrag(x: Float, y: Float) {
                listener.onDrag(x, y = y)
            }

            override fun onDragEnd() {
                listener.onDragEnd()
            }

            override fun onDragPrepare(dx: Float, dy: Float): Boolean {
                return listener.onDragPrepare(dx, dy)
            }

            override fun onDragStart() {
                listener.onDragStart()
//                onPause()
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


    override fun onPrepareProgressView(progressViewLiveData: MutableLiveData<Boolean>) {
//        this.progressViewLiveData = progressViewLiveData
    }

    override fun onPrepareLoadProgress(progressLiveData: MutableLiveData<Int>) {
//        this.progressLiveData = progressLiveData
    }

    override fun getVideoViewId(): Int {
        return R.id.audio_video
    }


    override fun getVideoLayoutId(): Int {
        return R.layout.item_larger_video
    }

}