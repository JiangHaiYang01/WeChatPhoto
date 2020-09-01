package com.starot.larger.builder

import android.content.Context
import android.content.Intent
import android.widget.ImageView
import com.starot.larger.act.DefSingleLargerAct
import com.starot.larger.bean.DefListData
import com.starot.larger.config.SingleLargerConfig
import com.starot.larger.impl.OnBuilderStart
import com.starot.larger.impl.OnCustomItemViewListener

class SingleBuilder(private val singleConfig: SingleLargerConfig?) :
    BaseBuilder<SingleBuilder>,
    OnBuilderStart {


    //设置图片源
    fun setImageList(images: List<ImageView>): SingleBuilder {
        singleConfig?.images = images
        return this
    }

    //默认的act 当设置默认 data 时候跳转此处
    override fun start(context: Context) {
        val intent = Intent(context, DefSingleLargerAct::class.java)
        context.startActivity(intent)
    }

    //跳转到自定义的act
    override fun start(context: Context, cls: Class<*>) {
        val intent = Intent(context, cls)
        context.startActivity(intent)
    }


    override fun setCustomImageListener(layoutId: Int, fullViewId: Int): SingleBuilder {
        setCustomImageListener(layoutId, fullViewId, null)
        return this
    }

    override fun setCustomImageListener(
        layoutId: Int,
        fullViewId: Int,
        listener: OnCustomItemViewListener?
    ): SingleBuilder {
        singleConfig?.itemLayout = layoutId
        singleConfig?.fullViewId = fullViewId
        singleConfig?.customItemViewListener = listener
        return this
    }

    override fun setCurrentIndex(pos: Int): SingleBuilder {
        singleConfig?.position = pos
        return this
    }

    override fun setDefData(data: List<DefListData>): SingleBuilder {
        singleConfig?.data = data
        return this
    }

    override fun setCustomData(data: List<Any>): SingleBuilder {
        singleConfig?.data = data
        return this
    }

}