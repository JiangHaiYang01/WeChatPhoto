package com.starot.larger.activity

import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.starot.larger.R
import com.starot.larger.view.image.PhotoView
import kotlin.math.abs

class DefLargerAct : LargerAct<String>() {

    companion object {
        const val TAG = "ALLENS"
    }

    //重写item 方法 加载图片 默认的只有一个image
    override fun item(itemView: View, position: Int, data: String?) {
        val image = itemView.findViewById<PhotoView>(R.id.image)
        Glide.with(this).load(data).into(image)
        image.setOnPhotoTapListener { _, _, _ ->
            Log.i(TAG, "click image exitAnim")
            exitAnim()
        }

        image.setOnSingleFlingListener { e1, e2, _, _ ->
            Log.i(TAG, "click drag ${e2.rawX - e1.rawX}, ${e2.rawY - e1.rawY}")
            return@setOnSingleFlingListener true
        }

        image.setOnViewDragListener { dx, dy ->
            Log.i(TAG, "click drag $dx, $dy")
//            if (image.scale <= 1.01f && abs(dy) > 30)
//                startDrag(dx, dy)

        }
    }


    override fun getData(): List<String>? {
        return intent.getStringArrayListExtra(IMAGE)
    }


    //设置 持续时间
    override fun setDuration(): Long {
        return 1000
    }


}