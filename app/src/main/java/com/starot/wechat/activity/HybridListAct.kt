package com.starot.wechat.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.starot.larger.bean.LargerBean
import com.starot.wechat.R
import com.starot.wechat.adapter.HybridListAdapter
import com.starot.wechat.adapter.ImageListAdapter
import com.starot.wechat.bean.ImageBean
import com.starot.wechat.bean.VideoBean
import com.starot.wechat.utils.Urls
import kotlinx.android.synthetic.main.activity_image_list.*


class HybridListAct : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_list)
        title = intent.getStringExtra("name")
        val type = intent.getIntExtra("type", 0)

        val list = arrayListOf<LargerBean>()

        val targetButtonSmall = Urls().getTargetButtonSmall()
        val targetButtonTarget = Urls().getTargetButtonTarget()
        for (index in targetButtonSmall.indices) {
            val element = ImageBean()
            element.fullUrl = targetButtonTarget[index]
            element.thumbnailsUrl = targetButtonSmall[index]
            list.add(element)
        }


        val audioImage = Urls().getAudioImage()
        val audio = Urls().getAudio()
        for (index in audioImage.indices) {
            val element = VideoBean()
            element.fullUrl = audio[index]
            element.thumbnailsUrl = audioImage[index]
            list.add(element)
        }



        act_list_ry.layoutManager = GridLayoutManager(this, 2)
        act_list_ry.adapter = HybridListAdapter(list, act_list_ry, type)
    }


}