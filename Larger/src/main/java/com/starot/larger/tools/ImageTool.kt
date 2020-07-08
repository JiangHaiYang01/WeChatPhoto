package com.starot.larger.tools

import android.content.Context
import android.graphics.Matrix
import android.graphics.Rect
import android.util.Log
import android.widget.ImageView
import com.starot.larger.anim.LargerAnim
import com.starot.larger.anim.TransitionEnterAnimHelper
import com.starot.larger.bean.ImageInfo
import com.starot.larger.bean.RectInfo


object ImageTool {

    const val TAG = "ALLENS"


    //获取ImageView 坐标
    private fun getImageRect(image: ImageView): RectInfo {

        Log.i(
            TAG,
            "======================================================================================="
        )
        // view的实际宽高
        val vWidth: Int = image.width
        val vHeight: Int = image.height
        Log.i(TAG, "view的实际宽高==========> vWidth $vWidth  vHeight $vHeight")

        // 得到imageView中的矩阵，准备得到drawable的拉伸比率
        val m: Matrix = image.imageMatrix
        val values = FloatArray(10)
        m.getValues(values)


        val drawable = image.drawable
        // drawable的本身宽高
        val dOriginalWidth: Int = drawable.intrinsicWidth
        val dOriginalHeight: Int = drawable.intrinsicHeight
        val dRatio = dOriginalWidth / dOriginalHeight //如果大于1，表示drawable宽>高
        Log.i(
            TAG,
            "drawable的本身宽高==========> dOriginalWidth $dOriginalWidth  dOriginalHeight $dOriginalHeight dRatio $dRatio"
        )

        //Image在绘制过程中的变换矩阵，从中获得x和y方向的缩放系数  value[0],[4]
        //得到drawable的实际显示时的宽高
        val dWidth = (dOriginalWidth * values[Matrix.MSCALE_X]).toInt()
        val dHeight = (dOriginalHeight * values[Matrix.MSCALE_Y]).toInt()
        Log.i(TAG, "得到drawable的实际显示时的宽高==========> dWidth $dWidth  dHeight $dHeight")

        //得到imageview的宽高和drawable的宽高的差值
        val w = vWidth - dWidth
        val h = vHeight - dHeight
        Log.i(TAG, "得到imageview的宽高和drawable的宽高的差值==========> w $w  h $h")
        val result = Rect()
        image.getGlobalVisibleRect(result)

        Log.i(TAG, "scaleType==========>" + image.scaleType)
        var isCenter = false
        when (image.scaleType) {
//            ImageView.ScaleType.MATRIX -> {
//            }
//            ImageView.ScaleType.FIT_XY -> {
//            }

            ImageView.ScaleType.FIT_START -> {
                result.right = (result.right - w + 0.5f).toInt()
                result.top = result.top
                result.left = result.left
                result.bottom = (result.bottom - h + 0.5f).toInt()
            }
            ImageView.ScaleType.FIT_CENTER -> {
                result.left = (result.left + (w / 2) + 0.5f).toInt()
                result.top = (result.top + (h / 2) + 0.5f).toInt()
                result.right = (result.right - (w / 2) + 0.5f).toInt()
                result.bottom = (result.bottom - (h / 2) + 0.5f).toInt()
            }
            ImageView.ScaleType.FIT_END -> {
                result.left = (result.left + w + 0.5f).toInt()
                result.top = (result.top + h + 0.5f).toInt()
                result.right = result.right
                result.bottom = result.bottom
            }
//            ImageView.ScaleType.CENTER -> {
//
//            }
//            ImageView.ScaleType.CENTER_CROP -> {
//
//            }
            ImageView.ScaleType.CENTER_INSIDE -> {
                result.left = (result.left + (w / 2) + 0.5f).toInt()
                result.top = (result.top + (h / 2) + 0.5f).toInt()
                result.right = (result.right - (w / 2) + 0.5f).toInt()
                result.bottom = (result.bottom - (h / 2) + 0.5f).toInt()
            }
            else->{
                isCenter = true
            }
        }
        Log.i(TAG, "result==========>$result")
        return RectInfo(result,isCenter)
    }

    //获取 图片信息
    fun getImageInfo(image: ImageView): ImageInfo {
        val imageRectInfo = getImageRect(image)
        val imageRect = imageRectInfo.rect
        //将所有的imageView 记录下来 后续会从中获取当前的图片大小进行判断
        LargerAnim.imageViews.add(image)
        return ImageInfo(
            imageRect.height().toFloat(),
            imageRect.width().toFloat(),
            imageRect.left.toFloat(),
            imageRect.top.toFloat(),
            imageRectInfo.isCenter
        )
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
        val imgScale: Float = getImgScale(width, height)
        val mWindowScale: Float = getWindowScale(context)
        val fl = if (imgScale >= mWindowScale) {
            width * 1.0f / getWindowWidth(context)
        } else {
            height * 1.0f / getWindowHeight(context)
        }
        Log.i(TAG, "原始图片 缩放度 ------>$fl  mWindowScale $mWindowScale  imgScale $imgScale")
        return fl
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