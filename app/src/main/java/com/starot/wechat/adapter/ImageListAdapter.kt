package com.starot.wechat.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.allens.largerglide.GlideImageLoader
import com.bumptech.glide.Glide
import com.starot.larger.Larger
import com.starot.larger.bean.DefListData
import com.starot.wechat.R
import kotlin.collections.ArrayList


class ImageListAdapter(
    private val data: ArrayList<DefListData>,
    private val recyclerView: RecyclerView
) :
    RecyclerView.Adapter<ImageListAdapter.ViewHolder>() {

    lateinit var context: Context

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image: ImageView = itemView.findViewById<ImageView>(R.id.item_image)
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
            .load(data[position].thumbnails)
            .into(holder.image)

        holder.itemView.setOnClickListener {
            Larger.create()
                .setDuration(300)
                .setImageLoad(GlideImageLoader(context))  //添加加载器
                .withListType()//这里展示的是列表类型的
                .setCurrentIndex(position)//下标
                .setRecyclerView(recyclerView)//recyclerview
                .setDefData(data) //添加默认的数据源
                .start(context) //启动默认的activity
        }
    }


}