package com.starot.wechat.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.allens.largerglide.GlideImageLoader
import com.bumptech.glide.Glide
import com.starot.larger.Larger
import com.starot.larger.bean.LargerBean
import com.starot.larger.impl.OnCustomImageLoadListener
import com.starot.larger.utils.LogUtils
import com.starot.wechat.R
import com.starot.wechat.bean.ImageBean
import kotlin.collections.ArrayList


class ImageListAdapter(
    private val data: ArrayList<ImageBean>,
    private val recyclerView: RecyclerView,
) :
    RecyclerView.Adapter<ImageListAdapter.ViewHolder>() {

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
            Larger.create()
                .withListType()//这里展示的是列表类型的
                .setImageLoad(GlideImageLoader(context))
                .setCustomListener(R.layout.item_custom_image, R.id.item_custom_image,object :OnCustomImageLoadListener{
                    override fun onCustomImageLoad(view: View, position: Int, data: LargerBean) {
                        view.findViewById<TextView>(R.id.item_custom_tv).setOnClickListener {
                            LogUtils.i("fuck you mom")
                        }
                    }
                })
                .setIndex(position)//下标
                .setMaxScale(4f)
                .setMediumScale(4f)
                .setAutomatic(false)
                .setDuration(3000)
                .setBackgroundColor(Color.BLACK)
                .setRecyclerView(recyclerView)//recyclerview
                .setData(data) //添加默认的数据源
                .start(context) //启动默认的activity
        }

    }


}