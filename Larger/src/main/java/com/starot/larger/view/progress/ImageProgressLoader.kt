package com.starot.larger.view.progress

import android.content.Context
import android.graphics.Color
import android.view.View
import com.starot.larger.enums.LoadImageStatus
import com.starot.larger.utils.LogUtils

class ImageProgressLoader(private val type: ProgressType) : ProgressLoader() {


    override fun onProgressChange(
        context: Context,
        view: View,
        progressId: Int,
        status: LoadImageStatus,
        position: Int
    ) {
        LogUtils.i("进度弹窗$position  是否不显示: $status")
        if (progressId == -1) {
            return
        }
        val progressView = view.findViewById<View>(progressId) ?: return
        when (status) {
            LoadImageStatus.LOAD_SHOW -> {
                progressView.visibility = View.VISIBLE
            }
            LoadImageStatus.LOAD_FAILED -> {
                progressView.visibility = View.INVISIBLE
            }
            LoadImageStatus.LOAD_SUCCESS -> {
                progressView.visibility = View.INVISIBLE
            }
            else -> {

            }
        }
        if (progressView is CircleProgressView) {
            setStyle(
                progressView,
                if (type == ProgressType.FULL) {
                    1
                } else {
                    0
                }
            )
        }

    }

    override fun onLoadProgress(view: View, progressId: Int, progress: Int, position: Int) {

        LogUtils.i("进度弹窗$position   progress:$progress")
        if (progressId == -1) {
            return
        }
        val progressView = view.findViewById<View>(progressId)
        if (progressView is CircleProgressView) {
            progressView.progress = progress
        }

    }


    enum class ProgressType {
        NONE,
        FULL
    }


    private fun setStyle(progressView: CircleProgressView?, style: Int) {
        when (style) {
            0 -> {
                progressView?.progressStyle = CircleProgressView.ProgressStyle.NORMAL
                progressView?.textColor = Color.parseColor("#ffffff")
                progressView?.normalBarColor = Color.parseColor("#00000000")
                progressView?.reachBarColor = Color.parseColor("#ffffff")
                progressView?.isTextVisible = true
                progressView?.textSuffix = "%"
                progressView?.reachBarSize = 3
                progressView?.textSize = 11
            }
            1 -> {
                progressView?.progressStyle = CircleProgressView.ProgressStyle.FILL_IN_ARC
                progressView?.outerColor = Color.parseColor("#ffffff")
                progressView?.normalBarColor = Color.parseColor("#00000000")
                progressView?.reachBarColor = Color.parseColor("#ffffff")
                progressView?.outerSize = 2
                progressView?.innerPadding = 2
            }
            else -> {
                progressView?.progressStyle = CircleProgressView.ProgressStyle.FILL_IN_ARC
                progressView?.outerColor = Color.parseColor("#ffffff")
                progressView?.normalBarColor = Color.parseColor("#00000000")
                progressView?.reachBarColor = Color.parseColor("#ffffff")
                progressView?.outerSize = 2
                progressView?.innerPadding = 2
            }
        }

    }
}