package com.starot.wechat

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.starot.wechat.larger.DefLargerAct
import kotlinx.android.synthetic.main.activity_def.*

class DefAct : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_def)


        val images = arrayListOf(
            "https://dss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=3656672875,122433815&fm=26&gp=0.jpg",
            "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=1766518855,3266540025&fm=26&gp=0.jpg",
            "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=3344832883,2462232837&fm=26&gp=0.jpg",
            "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=2480960326,1678684230&fm=26&gp=0.jpg"
        )


        Glide.with(this)
            .load(images[0])
            .into(image_1)
        Glide.with(this)
            .load(images[1])
            .into(image_2)
        Glide.with(this)
            .load(images[2])
            .into(image_3)
        Glide.with(this)
            .load(images[3])
            .into(image_4)

        image_1.setOnClickListener {
            startAct(0, images)
        }
        image_2.setOnClickListener {
            startAct(1, images)
        }
        image_3.setOnClickListener {
            startAct(2, images)
        }
        image_4.setOnClickListener {
            startAct(3, images)
        }
    }

    private fun startAct(index: Int, images: ArrayList<String>) {
        val intent = Intent(this, DefLargerAct::class.java)
        intent.putStringArrayListExtra(DefLargerAct.IMAGE, images)
        intent.putExtra(DefLargerAct.INDEX, index)
        startActivity(intent)
    }
}