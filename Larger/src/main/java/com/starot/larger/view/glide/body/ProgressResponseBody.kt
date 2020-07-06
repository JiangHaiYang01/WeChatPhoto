package com.starot.larger.view.glide.body


import android.util.Log
import com.starot.larger.view.glide.impl.ProgressListener
import com.starot.larger.view.glide.interceptor.ProgressInterceptor
import okhttp3.MediaType
import okhttp3.ResponseBody
import okio.*


class ProgressResponseBody(
    url: String,
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

        var currentProgress = 0
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
                listener?.onProgress(progress)
            }
            currentProgress = progress;
            return bytesRead
        }
    }
}
