package com.starot.wechat.larger

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.starot.larger.activity.LargerAct
import com.starot.wechat.R

class DefLargerAct : LargerAct<String>() {

    companion object {
        const val IMAGE = "images"
        const val INDEX = "index"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun getData(): List<String>? {
        return intent.getStringArrayListExtra(IMAGE)
    }

    override fun item(itemView: View, position: Int, data: String?) {
        val image = itemView.findViewById<ImageView>(com.starot.larger.R.id.image)
        Glide.with(this).load(data).into(image)
    }

    override fun getItemLayout(): Int {
        return R.layout.item_def
    }


}