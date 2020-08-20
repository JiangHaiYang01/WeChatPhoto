package com.allens.largerglide.body


import android.os.Handler
import android.util.Log
import com.allens.largerglide.impl.ProgressListener
import com.allens.largerglide.interceptor.ProgressInterceptor
import okhttp3.MediaType
import okhttp3.ResponseBody
import okio.*


class ProgressResponseBody(
    url: String,
    private val handler: Handler,
    private var responseBody: ResponseBody
) : ResponseBody() {

    private var listener: ProgressListener? = ProgressInterceptor.map[url]

    override fun contentLength(): Long {
        return responseBody.contentLength()
    }

    override fun contentType(): MediaType? {
        return responseBody.contentType()
    }

    override fun source(): BufferedSource {
        return Okio.buffer(ProgressSource(responseBody.source()))
    }

    inner class ProgressSource(source: Source) : ForwardingSource(source) {

        private var totalBytesRead: Long = 0

        private var currentProgress = 0
        override fun read(sink: Buffer, byteCount: Long): Long {
            val bytesRead = super.read(sink, byteCount)
            val fullLength = responseBody.contentLength()
            if (bytesRead == -1L) {
                totalBytesRead = fullLength;
            } else {
                totalBytesRead += bytesRead;
            }
            val progress = (100f * totalBytesRead / fullLength).toInt()
            if (progress != currentProgress) {
                handler.post {
                    listener?.onProgress(progress)
                }
            }
            currentProgress = progress;
            return bytesRead
        }
    }
}
