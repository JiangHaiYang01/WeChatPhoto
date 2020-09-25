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
import com.starot.larger.enums.LoadImageStatus
import com.starot.larger.enums.Orientation
import com.starot.larger.impl.OnCustomImageLoadListener
import com.starot.larger.impl.OnImageCacheListener
import com.starot.larger.impl.OnImageLoadListener
import com.starot.larger.view.progress.ImageProgressLoader
import com.starot.wechat.R
import com.starot.wechat.bean.ImageBean
import kotlin.collections.ArrayList


class ImageListAdapter(
    private val data: ArrayList<ImageBean>,
    private val recyclerView: RecyclerView,
    private val type: Int,
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

    private val listener = object : OnCustomImageLoadListener {
        override fun onDoBefore(
            imageLoader: OnImageLoadListener?,
            view: View,
            position: Int,
            data: LargerBean
        ) {
            val textView = view.findViewById<TextView>(R.id.item_custom_tv)
            val fullUrl = data.fullUrl
            if (fullUrl != null) {
                imageLoader?.checkCache(fullUrl, object : OnImageCacheListener {
                    override fun onCache(hasCache: Boolean) {
                        if (hasCache) {
                            textView.visibility = View.GONE
                        }
                    }
                })

                textView.setOnClickListener {
                    imageLoader?.load(
                        fullUrl,
                        position,
                        true,
                        view.findViewById(R.id.item_custom_image)
                    )
                }
            } else {
                textView.visibility = View.GONE
            }
        }

        override fun onDoAfter(
            imageLoader: OnImageLoadListener?,
            view: View,
            position: Int,
            data: LargerBean
        ) {
        }

        override fun onProgressChange(
            context: Context,
            view: View,
            progressId: Int,
            status: LoadImageStatus,
            position: Int
        ) {
            val textView = view.findViewById<TextView>(R.id.item_custom_tv)
            if (status == LoadImageStatus.LOAD_FAILED) {
                textView.visibility = View.GONE
            } else if (status == LoadImageStatus.LOAD_SUCCESS) {
                textView.visibility = View.GONE
            }
        }

        override fun onLoadProgress(view: View, progressId: Int, progress: Int, position: Int) {
        }
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(context)
            .load(data[position].thumbnailsUrl)
            .into(holder.image)


        holder.itemView.setOnClickListener {
            val withListType = Larger.create()
                .withListType()//这里展示的是列表类型的
                .setImageLoad(GlideImageLoader(context))   //图片加载器
                .setIndex(position)//下标
                .setDuration(300)//动画持续时间
                .setRecyclerView(recyclerView)//recyclerview
                .setData(data) //添加默认的数据源
                .setProgressLoaderUse(true) //使用加载框
                .setDebug(true)//设置显示日志
            when (type) {
                //默认
                0 -> {
                    withListType.setUpCanMove(true)//向上滑动有效
                        .setLoadNextFragment(true)//自定加载下一页大图
                }
                //加载原图自定义处理
                1 -> {
                    withListType
                        .setAutomatic(false)//设置不自动加载大图
                        .setCustomListener(
                            R.layout.item_custom_image,
                            R.id.item_custom_image,
                            R.id.custom_image_progress,
                            listener
                        )//自定义布局
                }
                //加载框的样式1
                2 -> {
                    withListType
                        .setAutomatic(false)//设置不自动加载大图
                        .setProgressType(ImageProgressLoader.ProgressType.FULL)
                        .setCustomListener(
                            R.layout.item_custom_image,
                            R.id.item_custom_image,
                            R.id.custom_image_progress,
                            listener
                        )//自定义布局
                }
                //加载框的样式2
                3 -> {
                    withListType
                        .setAutomatic(false)//设置不自动加载大图
                        .setProgressType(ImageProgressLoader.ProgressType.NONE)
                        .setCustomListener(
                            R.layout.item_custom_image,
                            R.id.item_custom_image,
                            R.id.custom_image_progress,
                            listener
                        )//自定义布局
                }
                //竖着滑动
                4 -> {
                    withListType
                        .setOrientation(Orientation.ORIENTATION_VERTICAL)//滑动的方向
                }
                //设置缩放大小
                5 -> {
                    withListType
                        .setMaxScale(4f)//设置最大比例
                        .setMediumScale(4f)//设置中间比例 不能超过最大比例
                }
                //设置背景颜色
                6 -> {
                    withListType.setBackgroundColor(Color.RED)
                }
            }
            withListType.start(context)
        }

    }


}