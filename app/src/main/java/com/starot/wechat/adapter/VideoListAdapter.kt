package com.starot.wechat.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.allens.largerglide.GlideImageLoader
import com.bumptech.glide.Glide
import com.example.largerloadvideo.LargerVideoLoad
import com.starot.larger.Larger
import com.starot.larger.enums.Orientation
import com.starot.wechat.R
import com.starot.wechat.bean.VideoBean
import kotlin.collections.ArrayList


class VideoListAdapter(
    private val data: ArrayList<VideoBean>,
    private val recyclerView: RecyclerView,
    private val type: Int,
) :
    RecyclerView.Adapter<VideoListAdapter.ViewHolder>() {

    private lateinit var context: Context

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image: ImageView = itemView.findViewById(R.id.item_image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_image, parent, false)
        context = parent.context
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return data.size
    }



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(context)
            .load(data[position].thumbnailsUrl)
            .into(holder.image)



        holder.itemView.setOnClickListener {
            val withListType = Larger.create()
                .withVideoMulti()//这里展示的是列表类型的
                .setImageLoad(GlideImageLoader(context))   //图片加载器
                .setVideoLoad(LargerVideoLoad(context))//视屏加载器
                .setIndex(position)//下标
                .setDebug(true)
                .setUpCanMove(true)
                .setDuration(3000)//动画持续时间
                .setRecyclerView(recyclerView)//recyclerview
                .setData(data) //添加默认的数据源
            when (type) {
                0 -> {
                }
            }
            withListType.start(context)
        }

    }


}