package com.starot.wechat.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.starot.larger.bean.LargerBean
import com.starot.wechat.R
import com.starot.wechat.adapter.VideoListAdapter
import com.starot.wechat.bean.ImageBean
import com.starot.wechat.bean.VideoBean
import com.starot.wechat.utils.Urls
import kotlinx.android.synthetic.main.activity_image_list.*


class VideoListAct : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_list)
        title = intent.getStringExtra("name")

        val list = arrayListOf<VideoBean>()

        val targetButtonSmall = Urls().getAudioImage()
        val targetButtonTarget = Urls().getAudio()
        for (index in targetButtonSmall.indices) {
            val element = VideoBean()
            element.fullUrl = targetButtonTarget[index]
            element.thumbnailsUrl = targetButtonSmall[index]
            list.add(element)
        }
        act_list_ry.layoutManager = GridLayoutManager(this, 2)
        act_list_ry.adapter = VideoListAdapter(list, act_list_ry)
    }



}