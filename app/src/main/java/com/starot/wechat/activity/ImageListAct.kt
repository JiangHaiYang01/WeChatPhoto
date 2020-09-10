package com.starot.wechat.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.starot.wechat.R
import com.starot.wechat.adapter.ImageListAdapter
import com.starot.wechat.bean.ImageBean
import com.starot.wechat.utils.Urls
import kotlinx.android.synthetic.main.activity_image_list.*


class ImageListAct : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_list)
        title = intent.getStringExtra("name")
        val type = intent.getIntExtra("type", 0)

        val list = arrayListOf<ImageBean>()

        val targetButtonSmall = Urls().getTargetButtonSmall()
        val targetButtonTarget = Urls().getTargetButtonTarget()
        for (index in targetButtonSmall.indices) {
            val element = ImageBean()
            element.fullUrl = targetButtonTarget[index]
            element.thumbnailsUrl = targetButtonSmall[index]
            list.add(element)
        }
        act_list_ry.layoutManager = GridLayoutManager(this, 2)
        act_list_ry.adapter = ImageListAdapter(list, act_list_ry, type)
    }


}