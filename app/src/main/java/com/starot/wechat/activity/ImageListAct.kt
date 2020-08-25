package com.starot.wechat.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.starot.larger.bean.DefListData
import com.starot.wechat.R
import com.starot.wechat.adapter.ImageListAdapter
import kotlinx.android.synthetic.main.activity_image_list.*
import java.util.ArrayList


class ImageListAct : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_list)
        title = intent.getStringExtra("name")

        val list = arrayListOf<DefListData>()

        val targetButtonSmall = getTargetButtonSmall()
        val targetButtonTarget = getTargetButtonTarget()
        for (index in targetButtonSmall.indices) {
            list.add(DefListData(targetButtonSmall[index], targetButtonTarget[index]))
        }


        val type = intent.getIntExtra("type", 0)
        when (type) {
            0-> {
                act_list_ry.layoutManager = GridLayoutManager(this, 2)
            }
            1 -> {
                act_list_ry.layoutManager =
                    LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            }
            2->{
                act_list_ry.layoutManager = GridLayoutManager(this, 2)
            }
        }
        act_list_ry.adapter = ImageListAdapter(list, act_list_ry,type)
    }


    private fun getTargetButtonTarget(): List<String> {
        val list: MutableList<String> =
            ArrayList()
        list.add("https://cdn.nlark.com/yuque/0/2020/jpeg/252337/1592643441654-assets/web-upload/771e09b0-aaf9-4308-bae0-cd5b3cb98817.jpeg")
        list.add("https://cdn.nlark.com/yuque/0/2020/jpeg/252337/1592643441557-assets/web-upload/94ed7774-2bed-4dbe-be54-080c2f8939a1.jpeg")
        list.add("https://cdn.nlark.com/yuque/0/2020/jpeg/252337/1592643441632-assets/web-upload/84d01b3f-7f28-4125-b3c7-8e5b5b15c0cb.jpeg")

        list.add("https://dss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=2738227336,3790339904&fm=26&gp=0.jpg")
        list.add("https://dss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=4272945591,798521482&fm=26&gp=0.jpg")
        list.add("https://dss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3782928332,855819166&fm=26&gp=0.jpg")
        list.add("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=1089806716,1356312236&fm=26&gp=0.jpg")
        list.add("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=239561221,4188985846&fm=26&gp=0.jpg")
        list.add("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=1373175324,819693906&fm=26&gp=0.jpg")
        list.add("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=1552629669,4197495188&fm=26&gp=0.jpg")
        list.add("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=1169404930,4142822803&fm=26&gp=0.jpg")
        list.add("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=3642306945,3051949181&fm=26&gp=0.jpg")
        list.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1598283468716&di=98b918a509a85109070ad0f93ec18de4&imgtype=0&src=http%3A%2F%2Fn.sinaimg.cn%2Ftranslate%2F537%2Fw1000h4337%2F20180727%2FpqGM-hfvkitx8894294.jpg")
        return list
    }

    private fun getTargetButtonSmall(): List<String> {
        val list: MutableList<String> =
            ArrayList()
        list.add("https://cdn.nlark.com/yuque/0/2020/jpeg/252337/1592643585276-assets/web-upload/6e2520de-544d-45b6-9892-ff228d14e175.jpeg")
        list.add("https://cdn.nlark.com/yuque/0/2020/jpeg/252337/1592643585330-assets/web-upload/85629ce4-bacf-4ab4-af6f-5a0a67931b68.jpeg")
        list.add("https://cdn.nlark.com/yuque/0/2020/jpeg/252337/1592643585344-assets/web-upload/3b178609-8aef-48f3-9ebc-41537149f13b.jpeg")

        list.add("https://dss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=2738227336,3790339904&fm=26&gp=0.jpg")
        list.add("https://dss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=4272945591,798521482&fm=26&gp=0.jpg")
        list.add("https://dss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3782928332,855819166&fm=26&gp=0.jpg")
        list.add("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=1089806716,1356312236&fm=26&gp=0.jpg")
        list.add("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=239561221,4188985846&fm=26&gp=0.jpg")
        list.add("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=1373175324,819693906&fm=26&gp=0.jpg")
        list.add("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=1552629669,4197495188&fm=26&gp=0.jpg")
        list.add("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=1169404930,4142822803&fm=26&gp=0.jpg")
        list.add("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=3642306945,3051949181&fm=26&gp=0.jpg")
        list.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1598283468716&di=98b918a509a85109070ad0f93ec18de4&imgtype=0&src=http%3A%2F%2Fn.sinaimg.cn%2Ftranslate%2F537%2Fw1000h4337%2F20180727%2FpqGM-hfvkitx8894294.jpg")
        return list
    }
}