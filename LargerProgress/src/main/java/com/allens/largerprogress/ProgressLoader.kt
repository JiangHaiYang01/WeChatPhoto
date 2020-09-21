package com.allens.largerprogress

import android.content.Context
import android.util.Log
import com.starot.larger.impl.OnLoadProgressListener
import com.starot.larger.utils.LogUtils

class ProgressLoader(private val type: ProgressType) : OnLoadProgressListener {

    private var dialog: ProgressDialog? = null

    override fun onProgressChange(context: Context, isFinish: Boolean) {
        LogUtils.i( "图片加载进度弹窗   是否不显示: $isFinish")
        if (dialog == null) {
            dialog = createDialog(context, type)
        }
        if (isFinish) {
            dialog?.dismiss()
            dialog = null
        } else {
            dialog?.show()
        }
    }

    override fun onLoadProgress(progress: Int) {
        LogUtils.i( "GlideProgressLoader onLoadProgress  progress $progress")
        if (dialog != null) {
            dialog?.setProgress(progress)
        }
    }

    private fun createDialog(context: Context, type: ProgressType): ProgressDialog {
        val progressDialog = ProgressDialog(context).create() as ProgressDialog
        progressDialog.setCanceledOnTouchOutside(false)
        progressDialog.setCancelable(false)
        progressDialog.setStyle(
            if (type == ProgressType.NONE) {
                0
            } else {
                1
            }
        )
        return progressDialog

    }


    enum class ProgressType {
        NONE,
        FULL
    }


}