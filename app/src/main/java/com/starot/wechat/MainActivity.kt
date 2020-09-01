package com.starot.wechat

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.starot.wechat.activity.AudioListAct
import com.starot.wechat.activity.ImageListAct
import com.starot.wechat.activity.SingleAct
import com.starot.wechat.activity.SingleVideoAct
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val arrayListOf = arrayListOf(
            "Grid Image 布局",
            "Linear Image 布局",
            "自定义item Image",
            "单独的ImageView",
            "单独的Audio",
            "Grid Audio 布局",
            "清理缓存"
        )

        for (index in arrayListOf.indices) {
            val button = Button(this)
            button.text = arrayListOf[index]
            main_ll.addView(button)
            button.setOnClickListener {


                when (index) {
                    arrayListOf.size - 1 -> {
                        Glide.get(this).clearMemory()
                        Thread(Runnable { Glide.get(this).clearDiskCache() }).start()
                        return@setOnClickListener
                    }

                    3 -> {
                        val intent = Intent(this, SingleAct::class.java)
                        intent.putExtra("name", arrayListOf[index])
                        intent.putExtra("type", index)
                        startActivity(intent)
                    }
                    4 -> {
                        val intent = Intent(this, SingleVideoAct::class.java)
                        intent.putExtra("name", arrayListOf[index])
                        intent.putExtra("type", index)
                        startActivity(intent)
                    }
                    5 -> {
                        val intent = Intent(this, AudioListAct::class.java)
                        intent.putExtra("name", arrayListOf[index])
                        intent.putExtra("type", index)
                        startActivity(intent)
                    }
                    else -> {
                        val intent = Intent(this, ImageListAct::class.java)
                        intent.putExtra("name", arrayListOf[index])
                        intent.putExtra("type", index)
                        startActivity(intent)
                    }
                }


            }
        }


    }
}