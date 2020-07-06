package com.starot.wechat.larger

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.starot.customprogress.CircleProgressView
import com.starot.larger.activity.LargerAct
import com.starot.wechat.glide.impl.ProgressListener
import com.starot.wechat.glide.interceptor.ProgressInterceptor
import com.starot.larger.view.image.PhotoView
import com.starot.wechat.R


class DefLargerAct : LargerAct<String>() {


    override fun getData(): List<String>? {
        return intent.getStringArrayListExtra(IMAGE)
    }


    //设置 持续时间
    override fun setDuration(): Long {
        return 200
    }

    //设置阻尼系数
    override fun setDamping(): Float {
        return 1.0f
    }

    override fun item(itemView: View, photoView: PhotoView, position: Int, data: String?) {
        if (data == null) {
            return
        }
        val progressView = itemView.findViewById<CircleProgressView>(R.id.progress)

        ProgressInterceptor.addListener(data, object : ProgressListener {
            override fun onProgress(progress: Int) {
                Log.i(TAG, "图片加载进度 $progress ${Thread.currentThread().name}")
                progressView.visibility = View.VISIBLE
                progressView.progress = progress
            }
        })

        //这里为了演示效果  取消了缓存  正常使用是不需要的
        val options = RequestOptions()
            .skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.NONE)


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
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    Log.i(TAG, "图片加载成功")
                    progressView.visibility = View.GONE
                    return false
                }
            })
            .into(photoView)
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
    override fun getPhotoView(): Int {
        return R.id.image
    }

}