package com.allens.largerprogress

import android.content.Context
import android.util.Log
import com.starot.larger.impl.OnLoadProgressListener

class GlideProgressLoader(private val type: ProgressType) : OnLoadProgressListener {

    private var dialog: ProgressDialog? = null

    override fun onProgressChange(context: Context, isGone: Boolean) {
        Log.i("allens_tag", "GlideProgressLoader onProgressChange  isGone $isGone")
        if (dialog == null) {
            dialog = createDialog(context, type)
        }
        if (isGone) {
            dialog?.dismiss()
        } else {
            dialog?.show()
        }
    }

    override fun onLoadProgress(progress: Int) {
        Log.i("allens_tag", "GlideProgressLoader onLoadProgress  progress $progress")
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