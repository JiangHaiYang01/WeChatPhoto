package com.starot.larger.builder.impl

import android.content.Context
import android.content.Intent
import com.starot.larger.act.CustomLargerAct
import com.starot.larger.config.LargerConfig
import com.starot.larger.enums.Orientation
import com.starot.larger.impl.OnLargerType


interface CommandConfig<T> {

    //是否直接向上就能够拖动，微信直接向上不可以拖动，这里默认false
    fun setUpCanMove(canMove: Boolean): T

    //是否自动加载下一页大图，默认不自动加载下一页
    fun setLoadNextFragment(auto: Boolean): T

    //是否打印日志
    fun setDebug(debug: Boolean): T


    //设置背景颜色
    fun setBackgroundColor(color: Int): T

    //设置加载方向
    fun setOrientation(orientation: Orientation): T

    //设置持续时间
    fun setDuration(duration: Long): T

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

}

