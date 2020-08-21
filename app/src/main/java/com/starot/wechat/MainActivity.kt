package com.starot.wechat

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.starot.wechat.activity.ImageListAct
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val arrayListOf = arrayListOf(
            "Grid 布局",
            "Linear 布局",
            "自定义item",
            "清理缓存"
        )

        for (index in arrayListOf.indices) {
            val button = Button(this)
            button.text = arrayListOf[index]
            main_ll.addView(button)
            button.setOnClickListener {

                if (index == arrayListOf.size - 1) {
                    Glide.get(this).clearMemory()
                    Thread(Runnable { Glide.get(this).clearDiskCache() }).start()
                    return@setOnClickListener
                }

                val intent = Intent(this, ImageListAct::class.java)
                intent.putExtra("name", arrayListOf[index])
                intent.putExtra("type", index)
                startActivity(intent)
            }
        }


    }
}