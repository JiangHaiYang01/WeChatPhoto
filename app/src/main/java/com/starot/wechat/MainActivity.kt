package com.starot.wechat

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.starot.wechat.activity.ImageListAct
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val arrayListOf = arrayListOf("Grid 布局", "Linear 布局")

        for (index in arrayListOf.indices) {
            val button = Button(this)
            button.text = arrayListOf[index]
            main_ll.addView(button)
            button.setOnClickListener {
                val intent = Intent(this, ImageListAct::class.java)
                intent.putExtra("name", arrayListOf[index])
                intent.putExtra("type", index)
                startActivity(intent)
            }
        }


    }
}