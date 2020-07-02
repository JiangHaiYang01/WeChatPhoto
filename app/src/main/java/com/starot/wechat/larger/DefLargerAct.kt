package com.starot.wechat.larger

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.starot.larger.activity.LargerAct
import com.starot.wechat.R

class DefLargerAct : LargerAct<String>() {


    override fun item(itemView: View, position: Int, data: String?) {
        val image = itemView.findViewById<ImageView>(com.starot.larger.R.id.image)
        Glide.with(this).load(data).into(image)

        itemView.setOnClickListener {
            //取消的动画
            exitAnim()
        }
    }

    override fun getItemLayout(): Int {
        return R.layout.item_def
    }


    override fun getData(): List<String>? {
        return intent.getStringArrayListExtra(IMAGE)
    }


    //设置 持续时间
    override fun setDuration(): Long {
        return 1000
    }


}