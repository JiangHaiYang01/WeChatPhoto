package com.starot.larger.tools

import android.content.Context
import android.graphics.Matrix
import android.graphics.Rect
import android.widget.ImageView
import com.starot.larger.bean.ImageInfo

object ImageTool {

    //获取ImageView 坐标
    private fun getImageRect(image: ImageView): Rect {
        val d = image.drawable
        val result = Rect()
        image.getGlobalVisibleRect(result)
        val tDrawableRect = d.bounds
        val drawableMatrix = image.imageMatrix
        val values = FloatArray(9)
        drawableMatrix.getValues(values)
        result.left = result.left + values[Matrix.MTRANS_X].toInt()
        result.top = result.top + values[Matrix.MTRANS_Y].toInt()
        result.right =
            (result.left + tDrawableRect.width() * if (values[Matrix.MSCALE_X].toInt() == 0) 1.0f else values[Matrix.MSCALE_X]).toInt()
        result.bottom =
            (result.top + tDrawableRect.height() * if (values[Matrix.MSCALE_Y].toInt() == 0) 1.0f else values[Matrix.MSCALE_Y]).toInt()
        return result
    }

    //获取 图片信息
    fun getImageInfo(image: ImageView): ImageInfo {
        val imageRect = getImageRect(image)
        return ImageInfo(imageRect.height().toFloat(), imageRect.width().toFloat(), imageRect.left.toFloat(), imageRect.top.toFloat())
    }


    //图片比例
     fun getImgScale(width: Float, height: Float): Float {
        return width * 1.0f / height
    }

    //小图 ---> 大图  缩放度
    fun getCurrentPicOriginalScale(
        context: Context,
        info: ImageInfo
    ): Float {
        val width: Float = info.width
        val height: Float = info.height
        val imgScale: Float = getImgScale(width.toFloat(), height.toFloat())
        val mWindowScale: Float = getWindowScale(context)
        return if (imgScale >= mWindowScale) {
            width * 1.0f / getWindowWidth(context)
        } else {
            height * 1.0f / getWindowHeight(context)
        }
    }

    //手机比例
     fun getWindowScale(context: Context): Float {
        return getWindowWidth(context.applicationContext) * 1.0f / getWindowHeight(context.applicationContext)
    }


    //手机屏幕 高度
     fun getWindowHeight(context: Context): Int {
        return context.applicationContext.resources.displayMetrics.heightPixels
    }

    //手机屏幕 宽度
     fun getWindowWidth(context: Context): Int {
        return context.applicationContext.resources.displayMetrics.widthPixels
    }
}