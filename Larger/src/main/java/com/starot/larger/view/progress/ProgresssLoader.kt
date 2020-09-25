package com.starot.larger.view.progress

import android.content.Context
import android.view.View
import com.starot.larger.enums.LoadImageStatus
import com.starot.larger.impl.OnLoadProgressListener

open class ProgressLoader : OnLoadProgressListener {
    override fun onProgressChange(
        context: Context,
        view: View,
        progressId: Int,
        status: LoadImageStatus,
        position: Int
    ) {

    }

    override fun onLoadProgress(view: View, progressId: Int, progress: Int, position: Int) {
    }

}