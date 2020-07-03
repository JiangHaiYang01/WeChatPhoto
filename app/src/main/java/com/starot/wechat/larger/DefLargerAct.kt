package com.starot.wechat.larger

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.starot.larger.activity.LargerAct
import com.starot.wechat.R

class DefLargerAct : LargerAct<String>() {


    //重写item 方法 加载图片 默认的只有一个image
    override fun item(itemView: View, position: Int, data: String?) {
        //父类的方法 必须要有
        super.item(itemView, position, data)
        val image = itemView.findViewById<ImageView>(com.starot.larger.R.id.image)
        Glide.with(this).load(data).into(image)
    }


    override fun getData(): List<String>? {
        return intent.getStringArrayListExtra(IMAGE)
    }


    //设置 持续时间
    override fun setDuration(): Long {
        return 1000
    }


}