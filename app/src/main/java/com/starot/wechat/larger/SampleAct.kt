package com.starot.wechat.larger

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.starot.customprogress.CircleProgressView
import com.starot.larger.activity.LargerAct
import com.starot.wechat.ImagesHelper
import com.starot.wechat.R
import com.starot.wechat.glide.impl.ProgressListener
import com.starot.wechat.glide.interceptor.ProgressInterceptor
import javax.sql.DataSource


//sample
class SampleAct : LargerAct<String>() {


    companion object {
        const val IMAGE = "images"
        const val INDEX = "index"
    }


    //添加数据源
    override fun getData(): ArrayList<String>? {
        return intent.getStringArrayListExtra(IMAGE)
    }

    //长按事件
    override fun onLongClickListener() {
        Toast.makeText(this, "长按图片", Toast.LENGTH_LONG).show()
    }

    //item 布局
    override fun getItemLayout(): Int {
        return R.layout.item_def
    }

    //一定要返回一个 PhotoView 的id  内部处理还是需要用到的
    override fun getPhotoViewId(): Int {
        return R.id.image
    }

    //当前是第几个图片  index 和 image 一一对应
    override fun getIndex(): Int {
        return intent.getIntExtra(INDEX, 0)
    }

    //设置 持续时间
    override fun setDuration(): Long {
        return 2000
    }

    //设置阻尼系数
    override fun setDamping(): Float {
        return 1.0f
    }

    //设置原来的图片源
    override fun getImageArrayList(): ArrayList<ImageView> {
        return ImagesHelper.images
    }

    //处理自己的业务逻辑
    override fun itemBindViewHolder(itemView: View, position: Int, data: String?) {
        if (data == null) {
            return
        }
        //这里用到了自己写的一个 进度条 可自定义
        val progressView = itemView.findViewById<CircleProgressView>(R.id.progress)

        //Glide 加载图片的进度 具体可参考代码
        ProgressInterceptor.addListener(data, object : ProgressListener {
            override fun onProgress(progress: Int) {
                progressView.visibility = View.VISIBLE
                progressView.progress = progress
            }
        })

        //这里为了演示效果  取消了缓存  正常使用是不需要的
        val options = RequestOptions()
//            .skipMemoryCache(true)
//            .diskCacheStrategy(DiskCacheStrategy.NONE)


        Glide.with(this)
            .load(data)
            .apply(options)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    Log.i(TAG, "图片加载失败")
                    progressView.visibility = View.GONE
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: com.bumptech.glide.load.DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    Log.i(TAG, "图片加载成功")
                    progressView.visibility = View.GONE
                    return false
                }
            })
            .into(itemView.findViewById(R.id.image))
    }


}