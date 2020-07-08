package com.starot.wechat

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.bumptech.glide.Glide
import com.starot.wechat.larger.SampleAct
import kotlinx.android.synthetic.main.activity_def.*

class DefAct : AppCompatActivity() {
    private val imageViews by lazy {
        arrayListOf<ImageView>(
            image_1,
            image_2,
            image_3,
            image_4,
            image_5,
            image_6,
            image_7,
            image_8
        )
    }

    private val images = arrayListOf(
        "http://img.netbian.com/file/2019/0722/46a77e637238b439e445a8e11279eb28.jpg",
        "http://img.netbian.com/file/2019/1214/552b1999aa4d5a2e75352fa2f6e93d51.jpg",
        "http://img.netbian.com/file/2020/0628/60cb9c1b9c5fecdb8ffe1e686ca7ef1d.jpg",
        "http://pic.jj20.com/up/allimg/1111/0H91Q05918/1PH9105918-1-1200.jpg",
        "http://pic.jj20.com/up/allimg/1111/0H91Q05918/1PH9105918-1-1200.jpg",
        "http://pic.jj20.com/up/allimg/1111/0H91Q05918/1PH9105918-1-1200.jpg",
        "http://pic.jj20.com/up/allimg/1111/0H91Q05918/1PH9105918-1-1200.jpg",
        "http://pic1.win4000.com/mobile/2020-06-12/5ee33a090097d.jpg"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_def)

        //记录小图的图片信息
        ImagesHelper.images = imageViews


        initLoad()
    }

    private fun initLoad() {
        for (index in imageViews.indices) {
            imageViews[index].scaleType = scaleType
            Glide.with(this)
                .load(images[index])
                .into(imageViews[index])
            imageViews[index].setOnClickListener {
                startAct(index, images)
            }
        }
    }

    private var scaleType = ImageView.ScaleType.CENTER_CROP

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menu?.add(1, 1, 1, "MATRIX")
        menu?.add(1, 2, 1, "FIT_XY")
        menu?.add(1, 3, 1, "FIT_START")
        menu?.add(1, 4, 1, "FIT_CENTER")
        menu?.add(1, 5, 1, "FIT_END")
        menu?.add(1, 6, 1, "CENTER")
        menu?.add(1, 7, 1, "CENTER_CROP")
        menu?.add(1, 8, 1, "CENTER_INSIDE")
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            1 -> {
                scaleType = ImageView.ScaleType.MATRIX
            }
            2 -> {
                scaleType = ImageView.ScaleType.FIT_XY
            }
            3 -> {
                scaleType = ImageView.ScaleType.FIT_START
            }
            4 -> {
                scaleType = ImageView.ScaleType.FIT_CENTER
            }
            5 -> {
                scaleType = ImageView.ScaleType.FIT_END
            }
            6 -> {
                scaleType = ImageView.ScaleType.CENTER
            }
            7 -> {
                scaleType = ImageView.ScaleType.CENTER_CROP
            }
            8 -> {
                scaleType = ImageView.ScaleType.CENTER_INSIDE
            }
        }
        initLoad()
        return super.onOptionsItemSelected(item)
    }

    private fun startAct(
        index: Int,
        images: ArrayList<String>
    ) {
        val intent = Intent(this, SampleAct::class.java)
        //传入图片信息 按需求自定义
        intent.putStringArrayListExtra(SampleAct.IMAGE, images)
        //传入当前的 index  用于处理viewpager
        intent.putExtra(SampleAct.INDEX, index)
        startActivity(intent)
    }
}