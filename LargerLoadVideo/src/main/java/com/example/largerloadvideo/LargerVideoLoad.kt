package com.example.largerloadvideo

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.view.View
import android.webkit.WebView
import android.widget.MediaController
import android.widget.VideoView
import androidx.lifecycle.MutableLiveData
import com.starot.larger.impl.OnVideoLoadListener
import com.starot.larger.utils.LogUtils

//视屏加载器
class LargerVideoLoad(private val context: Context) : OnVideoLoadListener,
    MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener {


    private var progressLiveData: MutableLiveData<Int>? = null
    private var progressViewLiveData: MutableLiveData<Boolean>? = null


    private lateinit var videoView: VideoView
    private var handler = Handler(Looper.getMainLooper())

    override fun load(url: String, videoView: VideoView) {
        LogUtils.i("start play audio")

        this.videoView = videoView

        //添加播放控制条
        val mediaController = MediaController(context)
        //取消进度
        mediaController.visibility = View.GONE
        videoView.setMediaController(mediaController)
        // 播放在线视频
        videoView.setVideoPath(Uri.parse(url).toString())
        videoView.setOnPreparedListener(this)
        videoView.setOnErrorListener(this)
        progressViewLiveData?.postValue(false)
        getProgress(videoView)

    }

    override fun pause() {
        videoView.pause()
    }

    override fun clear() {
        videoView.stopPlayback()
    }

    private fun getProgress(videoView: VideoView) {
        handler.postDelayed({
            val bufferPercentage = videoView.bufferPercentage
            LogUtils.i("audio bufferPercentage:$bufferPercentage")
            if (bufferPercentage != progressLiveData?.value) {
                progressLiveData?.postValue(bufferPercentage)
            }
            getProgress(videoView)

        }, 100)
    }

    override fun onPrepareProgressView(progressViewLiveData: MutableLiveData<Boolean>) {
        this.progressViewLiveData = progressViewLiveData
    }

    override fun onPrepareLoadProgress(progressLiveData: MutableLiveData<Int>) {
        this.progressLiveData = progressLiveData
    }

    override fun onPrepared(mediaPlayer: MediaPlayer?) {
        LogUtils.i("视屏装载完成")
        //取消设置背景图
        videoView.background = null
        progressLiveData?.postValue(100)
        progressViewLiveData?.postValue(true)
        handler.removeCallbacksAndMessages(null)
        //开始
        mediaPlayer?.start()
        //循环播放
        mediaPlayer?.isLooping = true
    }

    override fun onError(mp: MediaPlayer?, what: Int, extra: Int): Boolean {
        LogUtils.i("视屏异常 extra:${extra} what:$what")
        return false
    }
}