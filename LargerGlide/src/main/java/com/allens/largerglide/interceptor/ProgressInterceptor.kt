package com.allens.largerglide.interceptor

import android.os.Handler
import android.os.Looper
import com.allens.largerglide.impl.ProgressListener
import com.allens.largerglide.body.ProgressResponseBody
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.util.concurrent.ConcurrentHashMap


object ProgressInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        val response = chain.proceed(request)
        val url: String = request.url().toString()
        val body = response.body()
        val handler = Handler(Looper.getMainLooper())
        return response.newBuilder().body(body?.let {
            ProgressResponseBody(
                url,
                handler,
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
