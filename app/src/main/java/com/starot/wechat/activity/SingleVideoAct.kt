package com.starot.wechat.activity

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.MediaController
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.allens.largerglide.GlideImageLoader
import com.allens.largerprogress.GlideProgressLoader
import com.bumptech.glide.Glide
import com.example.largerloadvideo.LargerVideoLoad
import com.starot.larger.Larger
import com.starot.larger.bean.DefListData
import com.starot.larger.impl.OnCustomItemViewListener
import com.starot.larger.impl.OnReLoadFullImage
import com.starot.wechat.R
import kotlinx.android.synthetic.main.activity_single.*

//单个view 展示效果
class SingleVideoAct : AppCompatActivity() {

    private var mImage1 =
        "https://img2.woyaogexing.com/2019/05/26/165b72ea3617484e8d116c7f6761a369!400x400.jpeg"

    private var audio = "https://mp4.vjshi.com/2018-12-22/f4de0fcda0cf34707cf89d8d38825692.mp4"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single)
        title = intent.getStringExtra("name")

        Glide.with(this)
            .load(mImage1)
            .into(image_single_1)
        click(0, image_single_1)
    }


    private fun click(position: Int, view: View) {
        view.setOnClickListener {
            Larger.create()
                .setDuration(3000)
                .setAutomaticLoadFullImage(true)//自动加载大图
                .setImageLoad(GlideImageLoader(this))  //添加加载器
                .setVideoLoad(LargerVideoLoad(this))
                .setProgress(GlideProgressLoader(GlideProgressLoader.ProgressType.FULL)) //添加进度显示
                .asAudio()//兼容视屏
                .withSingleType()//这里展示的是单个或者不多的view
                .setImageList(arrayListOf(image_single_1))
                .setCurrentIndex(position)//下标
                //添加默认的数据源
                .setDefData(
                    arrayListOf(
                        DefListData(mImage1, audio)
                    )
                )
                .start(this) //启动默认的activity
        }

    }

}