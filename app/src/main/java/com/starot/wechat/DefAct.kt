package com.starot.wechat

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.starot.larger.activity.LargerAct
import com.starot.larger.tools.ImageTool
import com.starot.wechat.larger.DefLargerAct
import kotlinx.android.synthetic.main.activity_def.*

class DefAct : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_def)


        val images = arrayListOf(
            "http://img.netbian.com/file/2019/0722/46a77e637238b439e445a8e11279eb28.jpg",
            "http://img.netbian.com/file/2019/1214/552b1999aa4d5a2e75352fa2f6e93d51.jpg",
            "http://img.netbian.com/file/2020/0628/60cb9c1b9c5fecdb8ffe1e686ca7ef1d.jpg",
            "http://pic.jj20.com/up/allimg/1111/0H91Q05918/1PH9105918-1-1200.jpg"
        )


        Glide.with(this)
            .load(images[0])
            .into(image_1)
        Glide.with(this)
            .load(images[1])
            .into(image_2)
        Glide.with(this)
            .load(images[2])
            .into(image_3)
        Glide.with(this)
            .load(images[3])
            .into(image_4)

        image_1.setOnClickListener {
            startAct(0, images)
        }
        image_2.setOnClickListener {
            startAct(1, images)
        }
        image_3.setOnClickListener {
            startAct(2, images)
        }
        image_4.setOnClickListener {
            startAct(3, images)
        }
    }

    private fun startAct(index: Int, images: ArrayList<String>) {
        val intent = Intent(this, DefLargerAct::class.java)
        intent.putStringArrayListExtra(LargerAct.IMAGE, images)
        intent.putExtra(LargerAct.INDEX, index)
        intent.putParcelableArrayListExtra(
            LargerAct.ORIGINAL,
            arrayListOf(
                ImageTool.getImageInfo(image_1),
                ImageTool.getImageInfo(image_2),
                ImageTool.getImageInfo(image_3),
                ImageTool.getImageInfo(image_4)
            )
        )
        startActivity(intent)
    }
}