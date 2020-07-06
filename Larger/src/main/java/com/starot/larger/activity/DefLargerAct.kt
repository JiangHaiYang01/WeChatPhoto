package com.starot.larger.activity

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
import com.starot.larger.view.glide.impl.ProgressListener
import com.starot.larger.view.glide.interceptor.ProgressInterceptor
import com.starot.larger.view.image.PhotoView


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

        ProgressInterceptor.addListener(data, object : ProgressListener {
            override fun onProgress(progress: Int) {
                Log.i(TAG, "图片加载进度 $progress")
            }
        })
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
                    return false
                }
            })
            .into(photoView)
    }


    //长按事件
    override fun onLongClickListener() {
        Toast.makeText(this, "长按图片", Toast.LENGTH_LONG).show()
    }

}