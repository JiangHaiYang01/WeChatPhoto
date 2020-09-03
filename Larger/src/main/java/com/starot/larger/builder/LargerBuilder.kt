package com.starot.larger.builder

import android.content.Context
import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import com.starot.larger.act.CustomLargerAct
import com.starot.larger.config.LargerConfig
import com.starot.larger.impl.OnImageLoadListener
import com.starot.larger.impl.OnLargerType

class LargerBuilder(private val listConfig: LargerConfig?) {

    //pos
    fun setIndex(pos: Int): LargerBuilder {
        listConfig?.position = pos
        return this
    }

    //设置持续时间
    fun setDuration(duration: Long): LargerBuilder {
        listConfig?.duration = duration
        return this
    }


    //设置数据源
    fun setData(data: List<OnLargerType>): LargerBuilder {
        listConfig?.data = data
        return this
    }


    //开始
    fun start(context: Context) {
        val intent = Intent(context, CustomLargerAct::class.java)
        context.startActivity(intent)
    }

    //跳转到自定义的act
    fun start(context: Context, cls: Class<*>) {
        val intent = Intent(context, cls)
        context.startActivity(intent)
    }


    //列表的情况需要将 recyclerview 也传入
    fun setRecyclerView(recyclerView: RecyclerView): LargerBuilder {
        listConfig?.recyclerView = recyclerView
        return this
    }

    //设置图片加载器
    fun setImageLoad(imageLoad: OnImageLoadListener): LargerBuilder {
        listConfig?.imageLoad = imageLoad
        return this
    }

}