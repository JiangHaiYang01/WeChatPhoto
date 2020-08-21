package com.allens.largerprogress

import android.content.Context
import android.graphics.Color
import android.view.View
import dialog.BaseDialog

class ProgressDialog(context: Context) : BaseDialog(context) {
    override fun getLayoutId(): Int {
        return R.layout.dialog_progress
    }

    private var progressView: CircleProgressView? = null

    override fun onLayoutView(view: View) {
        progressView = view.findViewById(R.id.dialog_progress)
    }

    fun setProgress(progress: Int) {
        progressView?.progress = progress
    }

    fun setStyle(style: Int) {
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

    override fun getInflateWidthContent(): Boolean {
        return true
    }
}