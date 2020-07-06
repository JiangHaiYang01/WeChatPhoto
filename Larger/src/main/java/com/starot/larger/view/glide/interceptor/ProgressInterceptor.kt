package com.starot.larger.view.glide.interceptor

import android.util.Log
import com.starot.larger.view.glide.impl.ProgressListener
import com.starot.larger.view.glide.body.ProgressResponseBody
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.util.concurrent.ConcurrentHashMap


object ProgressInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        Log.i("Allens","intercept")
        val request: Request = chain.request()
        val response = chain.proceed(request)
        val url: String = request.url().toString()
        val body = response.body()
        Log.i("Allens", "intercept  body $body")
        return response.newBuilder().body(body?.let {
            ProgressResponseBody(
                url,
                it
            )
        }).build()
    }

     val map = ConcurrentHashMap<String, ProgressListener>()

    //入注册下载监听
    fun addListener(url: String, listener: ProgressListener) {
        map[url] = listener
    }

    //取消注册下载监听
    fun removeListener(url: String?) {
        map.remove(url)
    }
}
