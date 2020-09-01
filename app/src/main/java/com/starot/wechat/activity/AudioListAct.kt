package com.starot.wechat.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.starot.larger.bean.DefListData
import com.starot.wechat.R
import com.starot.wechat.adapter.ImageListAdapter
import kotlinx.android.synthetic.main.activity_image_list.*
import java.util.ArrayList


class AudioListAct : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_list)
        title = intent.getStringExtra("name")

        val list = arrayListOf<DefListData>()

        val targetButtonSmall = getTargetButtonSmall()
        val targetButtonTarget = getTargetButtonTarget()
        for (index in targetButtonSmall.indices) {
            list.add(DefListData(targetButtonSmall[index], targetButtonTarget[index]))
        }


        val type = intent.getIntExtra("type", 0)
        when (type) {
            5 -> {
                act_list_ry.layoutManager = GridLayoutManager(this, 2)
            }
//            1 -> {
//                act_list_ry.layoutManager =
//                    LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
//            }
//            2 -> {
//                act_list_ry.layoutManager = GridLayoutManager(this, 2)
//            }
        }
        act_list_ry.adapter = ImageListAdapter(list, act_list_ry, type)
    }


    private fun getTargetButtonTarget(): List<String> {
        val list: MutableList<String> =
            ArrayList()
        list.add("https://mp4.vjshi.com/2018-12-22/f4de0fcda0cf34707cf89d8d38825692.mp4")
        list.add("https://mp4.vjshi.com/2020-08-28/f17ccf7a47b0d96d8a033397f6eac7f5.mp4")
        list.add("https://mp4.vjshi.com/2020-03-17/6cf7d4f0ad7a573bf6d684515c4ee4e7.mp4")

        list.add("https://mp4.vjshi.com/2018-12-22/f4de0fcda0cf34707cf89d8d38825692.mp4")
        list.add("https://mp4.vjshi.com/2020-08-28/f17ccf7a47b0d96d8a033397f6eac7f5.mp4")
        list.add("https://mp4.vjshi.com/2020-03-17/6cf7d4f0ad7a573bf6d684515c4ee4e7.mp4")

        list.add("https://mp4.vjshi.com/2018-12-22/f4de0fcda0cf34707cf89d8d38825692.mp4")
        list.add("https://mp4.vjshi.com/2020-08-28/f17ccf7a47b0d96d8a033397f6eac7f5.mp4")
        list.add("https://mp4.vjshi.com/2020-03-17/6cf7d4f0ad7a573bf6d684515c4ee4e7.mp4")

        return list
    }

    private fun getTargetButtonSmall(): List<String> {
        val list: MutableList<String> =
            ArrayList()
        list.add("https://gitee.com/_Allens/BlogImage/raw/master/image/20200901152310.png")
        list.add("https://gitee.com/_Allens/BlogImage/raw/master/image/20200901152900.png")
        list.add("https://gitee.com/_Allens/BlogImage/raw/master/image/20200901153024.png")

        list.add("https://gitee.com/_Allens/BlogImage/raw/master/image/20200901152310.png")
        list.add("https://gitee.com/_Allens/BlogImage/raw/master/image/20200901152900.png")
        list.add("https://gitee.com/_Allens/BlogImage/raw/master/image/20200901153024.png")


        list.add("https://gitee.com/_Allens/BlogImage/raw/master/image/20200901152310.png")
        list.add("https://gitee.com/_Allens/BlogImage/raw/master/image/20200901152900.png")
        list.add("https://gitee.com/_Allens/BlogImage/raw/master/image/20200901153024.png")
        return list
    }
}