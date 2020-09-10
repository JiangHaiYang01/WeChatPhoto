package com.starot.wechat

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.starot.wechat.activity.ImageListAct
import com.starot.wechat.activity.VideoListAct
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val arrayListOf = arrayListOf(
            "图片列表布局",
            "加载原图自定义处理",
            "加载框的样式1",
            "加载框的样式2",
            "竖着滑动",
            "设置缩放大小",
            "设置背景颜色",
            "加载列表视屏",
            "清理缓存"
        )

        for (index in arrayListOf.indices) {
            val button = Button(this)
            button.text = arrayListOf[index]
            main_ll.addView(button)
            button.setOnClickListener {


                when (index) {
                    0->{

                    }
                    1->{
                        val intent = Intent(this, ImageListAct::class.java)
                        intent.putExtra("name", arrayListOf[index])
                        intent.putExtra("type", 1)
                        startActivity(intent)
                    }

                    2->{

                        Glide.get(this).clearMemory()
                        Thread(Runnable { Glide.get(this).clearDiskCache() }).start()

                        val intent = Intent(this, ImageListAct::class.java)
                        intent.putExtra("name", arrayListOf[index])
                        intent.putExtra("type", 2)
                        startActivity(intent)
                    }
                    3->{

                        Glide.get(this).clearMemory()
                        Thread(Runnable { Glide.get(this).clearDiskCache() }).start()

                        val intent = Intent(this, ImageListAct::class.java)
                        intent.putExtra("name", arrayListOf[index])
                        intent.putExtra("type", 3)
                        startActivity(intent)
                    }
                    4->{
                        val intent = Intent(this, ImageListAct::class.java)
                        intent.putExtra("name", arrayListOf[index])
                        intent.putExtra("type", 4)
                        startActivity(intent)
                    }
                    5->{
                        val intent = Intent(this, ImageListAct::class.java)
                        intent.putExtra("name", arrayListOf[index])
                        intent.putExtra("type", 5)
                        startActivity(intent)
                    }
                    6->{
                        val intent = Intent(this, ImageListAct::class.java)
                        intent.putExtra("name", arrayListOf[index])
                        intent.putExtra("type", 6)
                        startActivity(intent)
                    }
                    7->{
                        val intent = Intent(this, VideoListAct::class.java)
                        intent.putExtra("name", arrayListOf[index])
                        intent.putExtra("type", 0)
                        startActivity(intent)
                    }

                    arrayListOf.size - 1 -> {
                        Glide.get(this).clearMemory()
                        Thread(Runnable { Glide.get(this).clearDiskCache() }).start()
                        return@setOnClickListener
                    }
                }


            }
        }

    }
}