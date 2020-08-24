package com.starot.larger.builder

import android.content.Context
import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import com.starot.larger.act.DefListLargerAct
import com.starot.larger.bean.DefListData
import com.starot.larger.config.ListLargerConfig
import com.starot.larger.impl.OnBuilderStart
import com.starot.larger.impl.OnCustomItemViewListener

class ListBuilder(private val listConfig: ListLargerConfig?) :
    BaseBuilder<ListBuilder>,
    OnBuilderStart {


    //列表的情况需要将 recyclerview 也传入
    fun setRecyclerView(recyclerView: RecyclerView): ListBuilder {
        listConfig?.recyclerView = recyclerView
        return this
    }


    //默认的act 当设置默认 data 时候跳转此处
    override fun start(context: Context) {
        val intent = Intent(context, DefListLargerAct::class.java)
        context.startActivity(intent)
    }

    //跳转到自定义的act
    override fun start(context: Context, cls: Class<*>) {
        val intent = Intent(context, cls)
        context.startActivity(intent)
    }


    override fun setItemLayout(layoutId: Int): ListBuilder {
        listConfig?.itemLayout = layoutId
        return this
    }

    override fun setFullViewId(fullViewId: Int): ListBuilder {
        listConfig?.fullViewId = fullViewId
        return this
    }

    override fun registerCustomItemView(listener: OnCustomItemViewListener): ListBuilder {
        listConfig?.customItemViewListener = listener
        return this
    }

    override fun setCurrentIndex(pos: Int): ListBuilder {
        listConfig?.position = pos
        return this
    }

    override fun setDefData(data: List<DefListData>): ListBuilder {
        listConfig?.data = data
        return this
    }

    override fun setCustomData(data: List<Any>): ListBuilder {
        listConfig?.data = data
        return this
    }


}