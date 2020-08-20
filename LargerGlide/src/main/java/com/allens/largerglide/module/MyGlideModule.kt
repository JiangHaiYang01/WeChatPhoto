package com.allens.largerglide.module

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.module.AppGlideModule
import com.allens.largerglide.interceptor.ProgressInterceptor
import okhttp3.OkHttpClient
import java.io.InputStream

@GlideModule
class MyGlideModule : AppGlideModule() {
    override fun registerComponents(
        context: Context,
        glide: Glide,
        registry: Registry
    ) {
        //添加拦截器到Glide
        val builder = OkHttpClient.Builder()
        builder.addInterceptor(ProgressInterceptor)
        val okHttpClient = builder.build()

        registry.replace(
            GlideUrl::class.java,
            InputStream::class.java,
            OkHttpUrlLoader.Factory(okHttpClient)
        )
    }


}