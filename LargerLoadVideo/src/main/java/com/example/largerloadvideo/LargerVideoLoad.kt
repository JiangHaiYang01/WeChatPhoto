package com.example.largerloadvideo

import android.content.Context
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.starot.larger.impl.OnVideoLoadListener
import com.starot.larger.utils.LogUtils
import org.salient.artplayer.conduction.PlayerState
import org.salient.artplayer.player.SystemMediaPlayer
import org.salient.artplayer.ui.VideoView

//视屏加载器
class LargerVideoLoad(private val context: Context) : OnVideoLoadListener {


    private var progressLiveData: MutableLiveData<Int>? = null
    private var progressViewLiveData: MutableLiveData<Boolean>? = null


    private lateinit var videoView: VideoView


    override fun onAudioThumbnail(itemView: View, drawable: Drawable) {
        LogUtils.i("LargerVideoLoad start onAudioThumbnail")
        val videoView = itemView.findViewById<VideoView>(getVideoViewId())
        videoView.cover.setImageDrawable(drawable)
    }

    override fun load(url: String, view: View) {
        LogUtils.i("start play audio")

        //显示加载等待框
        progressViewLiveData?.postValue(false)

        this.videoView = view.findViewById(getVideoViewId())
        videoView.mediaPlayer = SystemMediaPlayer().apply {
            setDataSource(context, Uri.parse(url))
        }
        videoView.prepare()


        //缓冲状态发生改变
        videoView.mediaPlayer?.bufferingProgressLD?.observeForever {
            LogUtils.i("audio bufferPercentage:$it")
            if (it != progressLiveData?.value) {
                progressLiveData?.postValue(it)
            }
        }

        //播放器的状态
        videoView.mediaPlayer?.playerStateLD?.observeForever {
            LogUtils.i("播放器的状态 :${it.code}")
            if (it.code == PlayerState.STARTED.code) {
                LogUtils.i("播放器的状态 STARTED")
                //取消加载框
                progressViewLiveData?.postValue(true)
            } else if (it.code == PlayerState.ERROR.code || it.code == PlayerState.COMPLETED.code) {
                progressViewLiveData?.postValue(true)
            }
        }
    }

    override fun pause() {
        LogUtils.i("LargerVideoLoad pause")
        videoView.pause()
    }

    override fun stop() {
        LogUtils.i("LargerVideoLoad stop")
        videoView.stop()
        videoView.release()
    }


    override fun onPrepareProgressView(progressViewLiveData: MutableLiveData<Boolean>) {
        this.progressViewLiveData = progressViewLiveData
    }

    override fun onPrepareLoadProgress(progressLiveData: MutableLiveData<Int>) {
        this.progressLiveData = progressLiveData
    }

    override fun getVideoViewId(): Int {
        return R.id.audio_videoView
    }

    override fun getVideoFullId(): Int {
        return R.id.audio_image
    }

    override fun getVideoLayoutId(): Int {
        return R.layout.item_larger_video
    }
}