package com.starot.wechat.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.starot.wechat.R
import com.starot.wechat.adapter.ImageListAdapter
import kotlinx.android.synthetic.main.activity_image_list.*


class ImageListAct :AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_list)
        title = "列表布局"

        val lists = arrayListOf<String>()
        for (index in 0..10) {
            lists.add("https://dss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=2738227336,3790339904&fm=26&gp=0.jpg")
            lists.add("https://dss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=4272945591,798521482&fm=26&gp=0.jpg")
            lists.add("https://dss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3782928332,855819166&fm=26&gp=0.jpg")
            lists.add("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=1089806716,1356312236&fm=26&gp=0.jpg")
            lists.add("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=239561221,4188985846&fm=26&gp=0.jpg")
            lists.add("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=1373175324,819693906&fm=26&gp=0.jpg")
            lists.add("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=1552629669,4197495188&fm=26&gp=0.jpg")
            lists.add("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=1169404930,4142822803&fm=26&gp=0.jpg")
            lists.add("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=3642306945,3051949181&fm=26&gp=0.jpg")
        }

        act_list_ry.layoutManager = GridLayoutManager(this,2)
        act_list_ry.adapter = ImageListAdapter(lists,act_list_ry)
    }
}