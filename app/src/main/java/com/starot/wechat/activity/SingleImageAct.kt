package com.starot.wechat.activity

import android.os.Bundle
import com.allens.largerglide.GlideImageLoader
import com.bumptech.glide.Glide
import com.starot.larger.Larger
import com.starot.wechat.R
import com.starot.wechat.bean.ImageBean
import com.starot.wechat.utils.Urls
import kotlinx.android.synthetic.main.activity_image_single.*


class SingleImageAct : BaseAct() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_single)
        title = intent.getStringExtra("name")


        val list = arrayListOf<ImageBean>()

        val targetButtonSmall = Urls().getTargetButtonSmall()
        val targetButtonTarget = Urls().getTargetButtonTarget()
        for (index in targetButtonSmall.indices) {
            if (index == 1) {
                break
            }
            val element = ImageBean()
            element.fullUrl = targetButtonTarget[index]
            element.thumbnailsUrl = targetButtonSmall[index]
            list.add(element)

        }

        Glide.with(this).load(list[0].thumbnailsUrl).into(src_image)

        src_image.setOnClickListener {
            Larger.create()
                .withImageSingle()//这里展示的是单个图片
                .setImageLoad(GlideImageLoader(this))   //图片加载器
                .setDuration(300)//动画持续时间
                .setImage(src_image)//设置imageView
                .setData(list[0]) //添加默认的数据源
                .start(this)
        }
    }


}