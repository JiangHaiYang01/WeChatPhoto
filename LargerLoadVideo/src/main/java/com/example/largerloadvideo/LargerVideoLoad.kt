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


//视屏加载器
class LargerVideoLoad(private val context: Context) : OnVideoLoadListener {


    private var progressLiveData: MutableLiveData<Int>? = null
    private var progressViewLiveData: MutableLiveData<Boolean>? = null

    override fun getPoster(view: View): ImageView {
        val video = view.findViewById<JzvdStd>(getVideoViewId())
        return video.posterImageView
    }


    override fun loadVideo(data: LargerBean, view: View,  listener: OnLargerDragListener) {
        val video = view.findViewById<MyVideoView>(getVideoViewId())
        video.setDragListener(listener)
        video.setUp(data.fullUrl, "", Jzvd.SCREEN_NORMAL)

    }


    override fun onRelease() {
        Jzvd.releaseAllVideos()
    }

    override fun onPause() {
        JZUtils.clearSavedProgress(context.applicationContext, null)
        Jzvd.goOnPlayOnPause()
    }

    override fun onResume() {
        Jzvd.goOnPlayOnResume()
    }


    override fun onPrepareProgressView(progressViewLiveData: MutableLiveData<Boolean>) {
        this.progressViewLiveData = progressViewLiveData
    }

    override fun onPrepareLoadProgress(progressLiveData: MutableLiveData<Int>) {
        this.progressLiveData = progressLiveData
    }

    override fun getVideoViewId(): Int {
        return R.id.audio_video
    }


    override fun getVideoLayoutId(): Int {
        return R.layout.item_larger_video
    }

}