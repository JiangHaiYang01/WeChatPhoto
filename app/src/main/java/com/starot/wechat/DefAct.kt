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
//            "https://upload-images.jianshu.io/upload_images/9947322-01b4a6dba68e95dd.jpg",
//            "https://upload-images.jianshu.io/upload_images/9947322-01b4a6dba68e95dd.jpg",
//            "https://upload-images.jianshu.io/upload_images/9947322-01b4a6dba68e95dd.jpg",
//            "https://upload-images.jianshu.io/upload_images/9947322-01b4a6dba68e95dd.jpg",
//            "https://upload-images.jianshu.io/upload_images/9947322-01b4a6dba68e95dd.jpg",
//            "https://upload-images.jianshu.io/upload_images/9947322-01b4a6dba68e95dd.jpg",
//            "https://upload-images.jianshu.io/upload_images/9947322-01b4a6dba68e95dd.jpg",
//            "https://upload-images.jianshu.io/upload_images/9947322-01b4a6dba68e95dd.jpg"
            "http://img.netbian.com/file/2019/0722/46a77e637238b439e445a8e11279eb28.jpg",
            "http://img.netbian.com/file/2019/1214/552b1999aa4d5a2e75352fa2f6e93d51.jpg",
            "http://img.netbian.com/file/2020/0628/60cb9c1b9c5fecdb8ffe1e686ca7ef1d.jpg",
            "http://pic.jj20.com/up/allimg/1111/0H91Q05918/1PH9105918-1-1200.jpg",
            "http://pic.jj20.com/up/allimg/1111/0H91Q05918/1PH9105918-1-1200.jpg",
            "http://pic.jj20.com/up/allimg/1111/0H91Q05918/1PH9105918-1-1200.jpg",
            "http://pic.jj20.com/up/allimg/1111/0H91Q05918/1PH9105918-1-1200.jpg",
            "http://pic1.win4000.com/mobile/2020-06-12/5ee33a090097d.jpg"
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
        Glide.with(this)
            .load(images[4])
            .into(image_5)
        Glide.with(this)
            .load(images[5])
            .into(image_6)
        Glide.with(this)
            .load(images[6])
            .into(image_7)
        Glide.with(this)
            .load(images[7])
            .into(image_8)

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
        image_5.setOnClickListener {
            startAct(4, images)
        }
        image_6.setOnClickListener {
            startAct(5, images)
        }
        image_7.setOnClickListener {
            startAct(6, images)
        }
        image_8.setOnClickListener {
            startAct(7, images)
        }
    }

    private fun startAct(index: Int, images: ArrayList<String>) {
        val intent = Intent(this, DefLargerAct::class.java)
        //传入图片信息 这里可所以类型
        intent.putStringArrayListExtra(LargerAct.IMAGE, images)
        //传入当前的 index  用于处理viewpager2 务必添加
        intent.putExtra(LargerAct.INDEX, index)
        //传入图片的位置信息，点击以后的动画效果需要 务必添加
        intent.putParcelableArrayListExtra(
            LargerAct.ORIGINAL,
            arrayListOf(
                ImageTool.getImageInfo(image_1),
                ImageTool.getImageInfo(image_2),
                ImageTool.getImageInfo(image_3),
                ImageTool.getImageInfo(image_4),
                ImageTool.getImageInfo(image_5),
                ImageTool.getImageInfo(image_6),
                ImageTool.getImageInfo(image_7),
                ImageTool.getImageInfo(image_8)
            )
        )
        startActivity(intent)
    }
}