package com.starot.larger.activity

import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.starot.larger.R
import com.starot.larger.view.image.OnViewDragListener
import com.starot.larger.view.image.PhotoView
import kotlin.math.abs

class DefLargerAct : LargerAct<String>() {



    override fun getData(): List<String>? {
        return intent.getStringArrayListExtra(IMAGE)
    }


    //设置 持续时间
    override fun setDuration(): Long {
        return 2000
    }

    override fun item(itemView: View, photoView: PhotoView, position: Int, data: String?) {
        Glide.with(this).load(data).into(photoView)
    }


}