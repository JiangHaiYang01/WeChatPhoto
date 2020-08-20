package com.starot.larger.builder

import android.content.Context
import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import com.starot.larger.act.DefLargerAct
import com.starot.larger.bean.DefListData
import com.starot.larger.config.ListLargerConfig
import com.starot.larger.impl.OnBuilderStart

class ListBuilder(private val listConfig: ListLargerConfig?) : OnBuilderStart {


    //列表的情况需要将 recyclerview 也传入
    fun setRecyclerView(recyclerView: RecyclerView): ListBuilder {
        listConfig?.recyclerView = recyclerView
        return this
    }

    //设置列表的布局 如果不设置有默认的样式
    fun setItemLayout(layoutId: Int): ListBuilder {
        listConfig?.itemLayout = layoutId
        return this
    }

    //大图的ImageViewID  不设置的话有默认
    fun setFullViewId(fullViewId: Int): ListBuilder {
        listConfig?.fullViewId = fullViewId
        return this
    }


    //设置当前是第几个
    fun setCurrentIndex(pos: Int): ListBuilder {
        listConfig?.position = pos
        return this
    }


    //设置默认的类型
    fun setDefData(data: List<DefListData>): ListBuilder {
        listConfig?.data = data
        return this
    }

    //设置数自定义据源 配合自定义的activity 使用
    fun setCustomData(data: List<Any>): ListBuilder {
        listConfig?.data = data
        return this
    }


    //默认的act 当设置默认 data 时候跳转此处
    override fun start(context: Context) {
        val intent = Intent(context, DefLargerAct::class.java)
        context.startActivity(intent)
    }

    //跳转到自定义的act
    override fun start( context: Context,cls: Class<*>){
        val intent = Intent(context,cls)
        context.startActivity(intent)
    }


}