package com.starot.wechat.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.allens.largerglide.GlideImageLoader
import com.allens.largerprogress.GlideProgressLoader
import com.bumptech.glide.Glide
import com.starot.larger.Larger
import com.starot.larger.bean.DefListData
import com.starot.larger.impl.OnCustomItemViewListener
import com.starot.larger.impl.OnReLoadFullImage
import com.starot.wechat.R
import kotlinx.android.synthetic.main.activity_single.*

class SingleAct : AppCompatActivity() {

    private var mImage1 =
        "https://img2.woyaogexing.com/2019/05/26/165b72ea3617484e8d116c7f6761a369!400x400.jpeg"
    private var mImage2 =
        "https://img2.woyaogexing.com/2019/05/26/9e20a87ad9f442fe92860c7c500c5f75!400x400.jpeg"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single)
        title = intent.getStringExtra("name")

        Glide.with(this)
            .load(mImage1)
            .into(image_single_1)

//        Glide.with(this)
//            .load(mImage2)
//            .into(image_single_2)

        click(0, image_single_1)
//        click(1, image_single_2)
    }


    fun click(position: Int, view: View) {
        view.setOnClickListener {
            Larger.create()
                .setDuration(300)
                .setAutomaticLoadFullImage(false)//不自动加载大图
                .setImageLoad(GlideImageLoader(this))  //添加加载器
                .setProgress(GlideProgressLoader(GlideProgressLoader.ProgressType.FULL)) //添加进度显示
                .withSingleType()//这里展示的是列表类型的
                .setImageList(arrayListOf(image_single_1, image_single_2))
                .setCurrentIndex(position)//下标
                .setItemLayout(R.layout.item_custom_image)
                .registerCustomItemView(object : OnCustomItemViewListener {
                    //自定义处理item
                    override fun itemBindViewHolder(
                        listener: OnReLoadFullImage,
                        itemView: View,
                        position: Int,
                        data: Any?
                    ) {
                        itemView.findViewById<TextView>(R.id.item_custom_tv)
                            .setOnClickListener {
                                Log.i("allens_tag", "点击查看原图")
                                listener.reLoadFullImage()
                            }
                    }
                })
                .setFullViewId(R.id.item_custom_image)
                .setDefData(
                    arrayListOf(
                        DefListData(mImage1, mImage1)
//                        DefListData(mImage2, mImage2)
                    )
                ) //添加默认的数据源
                .start(this) //启动默认的activity
        }

    }

}