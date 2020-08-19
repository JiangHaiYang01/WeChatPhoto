package com.starot.larger

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.starot.larger.act.DefLargerAct
import java.io.Serializable

object Larger {


    fun create(): Builder {
        return Builder(LargerConfig())
    }


    var config: LargerConfig? = null

    class Builder(private val largerConfig: LargerConfig) {


        //设置持续时间
        fun setDuration(duration: Long): Builder {
            largerConfig.duration = duration
            return this
        }


        //设置当前是第几个
        fun setCurrentIndex(pos: Int): Builder {
            largerConfig.position = pos
            return this
        }

        //设置缩略图集合
        fun setThumbnailData(thumbnails: List<String>): Builder {
            largerConfig.thumbnails = thumbnails
            return this
        }

        //设置大图集合
        fun setFullData(full: List<String>): Builder {
            largerConfig.fulls = full
            return this
        }

        //列表的情况需要将 recyclerview 也传入
        fun setRecyclerView(recyclerView: RecyclerView): Builder {
            largerConfig.recyclerView = recyclerView
            return this
        }

        fun start(context: Context) {
            config = largerConfig
            val intent = Intent(context, DefLargerAct::class.java)
            context.startActivity(intent)
        }
    }

    data class LargerConfig(
        //持续时间
        var duration: Long = 300,
        //当前的下标
        var position: Int = 0,
        //缩略图列表
        var thumbnails: List<String>? = null,
        //大图列表
        var fulls: List<String>? = null,
        //列表
        var recyclerView: RecyclerView? = null
    )


}