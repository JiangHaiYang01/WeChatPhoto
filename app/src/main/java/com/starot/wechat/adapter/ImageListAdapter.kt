package com.starot.wechat.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.allens.largerglide.GlideImageLoader
import com.allens.largerprogress.GlideProgressLoader
import com.bumptech.glide.Glide
import com.starot.larger.Larger
import com.starot.larger.bean.DefListData
import com.starot.larger.impl.OnCustomItemViewListener
import com.starot.larger.impl.OnReLoadFullImage
import com.starot.wechat.R
import kotlin.collections.ArrayList


class ImageListAdapter(
    private val data: ArrayList<DefListData>,
    private val recyclerView: RecyclerView,
    private val type: Int
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


    private var listener = object : OnCustomItemViewListener {
        //自定义处理item
        override fun itemBindViewHolder(
            listener: OnReLoadFullImage,
            itemView: View,
            position: Int,
            data: Any?
        ) {
            itemView.findViewById<TextView>(R.id.item_custom_tv)
                .setOnClickListener {
                    Log.i("allens_tag", "点击查看原图")
                    listener.reLoadFullImage()
                }
        }

        override fun itemImageHasCache(itemView: View, position: Int, hasCache: Boolean) {
            itemView.findViewById<TextView>(R.id.item_custom_tv).visibility =
                if (hasCache) {
                    View.INVISIBLE
                } else {
                    View.VISIBLE
                }
        }

        override fun itemImageFullLoad(itemView: View, position: Int) {
            itemView.findViewById<TextView>(R.id.item_custom_tv).visibility = View.INVISIBLE
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(context)
            .load(data[position].thumbnails)
            .into(holder.image)


        when (type) {
            0 -> {
                holder.itemView.setOnClickListener {
                    Larger.create()
                        .setDuration(300)
                        .setImageLoad(GlideImageLoader(context))  //添加加载器
                        .setProgress(GlideProgressLoader(GlideProgressLoader.ProgressType.FULL)) //添加进度显示
                        .withListType()//这里展示的是列表类型的
                        .setCurrentIndex(position)//下标
                        .setRecyclerView(recyclerView)//recyclerview
                        .setDefData(data) //添加默认的数据源
                        .start(context) //启动默认的activity
                }
            }

            1 -> {
                holder.itemView.setOnClickListener {
                    Larger.create()
                        .setDuration(300)
                        .setImageLoad(GlideImageLoader(context))  //添加加载器
                        .setProgress(GlideProgressLoader(GlideProgressLoader.ProgressType.FULL)) //添加进度显示
                        .withListType()//这里展示的是列表类型的
                        .setCurrentIndex(position)//下标
                        .setRecyclerView(recyclerView)//recyclerview
                        .setDefData(data) //添加默认的数据源
                        .start(context) //启动默认的activity
                }
            }
            2 -> {
                holder.itemView.setOnClickListener {
                    Larger.create()
                        .setDuration(300)
                        .setAutomaticLoadFullImage(false)//不自动加载大图
                        .setImageLoad(GlideImageLoader(context))  //添加加载器
                        .setProgress(GlideProgressLoader(GlideProgressLoader.ProgressType.FULL)) //添加进度显示
                        .withListType()//这里展示的是列表类型的
                        .setCurrentIndex(position)//下标
                        .setCustomImageListener(
                            R.layout.item_custom_image,
                            R.id.item_custom_image,
                            listener
                        )
                        .setRecyclerView(recyclerView)//recyclerview
                        .setDefData(data) //添加默认的数据源
                        .start(context) //启动默认的activity
                }
            }
        }
    }


}