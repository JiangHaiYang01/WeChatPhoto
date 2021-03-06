package com.starot.wechat.activity

import android.os.Bundle
import com.allens.largerglide.GlideImageLoader
import com.bumptech.glide.Glide
import com.example.largerloadvideo.LargerVideoLoad
import com.starot.larger.Larger
import com.starot.wechat.R
import com.starot.wechat.bean.VideoBean
import com.starot.wechat.utils.Urls
import kotlinx.android.synthetic.main.activity_image_single.*


class SingleAudioAct : BaseAct() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_single)
        title = intent.getStringExtra("name")


        val list = arrayListOf<VideoBean>()

        val targetButtonSmall = Urls().getAudioImage()
        val targetButtonTarget = Urls().getAudio()
        for (index in targetButtonSmall.indices) {
            if (index == 2) {
                val element = VideoBean()
                element.fullUrl = targetButtonTarget[index]
                element.thumbnailsUrl = targetButtonSmall[index]
                list.add(element)

            }
        }

        Glide.with(this).load(list[0].thumbnailsUrl).into(src_image)

        src_image.setOnClickListener {
            Larger.create()
                .withVideoSingle()//这里展示的是列表类型的
                .setImageLoad(GlideImageLoader(this))   //图片加载器
                .setDuration(3000)//动画持续时间
                .setVideoLoad(LargerVideoLoad(this))
                .setImage(src_image)//设置imageView
                .setData(list[0]) //添加默认的数据源
                .start(this)
        }
    }


}