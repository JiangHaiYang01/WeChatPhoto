package com.starot.larger.anim

import android.animation.Animator.AnimatorListener
import android.content.Context
import android.graphics.Matrix
import android.graphics.Rect
import android.view.View
import android.widget.ImageView

class AnimTool(private val context: Context) {


    //屏幕高度
    private val height = context.applicationContext.resources.displayMetrics.heightPixels

    //屏幕宽度
    private val width = context.applicationContext.resources.displayMetrics.widthPixels

    //屏幕的比例
    private val windowScale: Float = (width * 1.0 / height).toFloat()

    //图片比例
    private fun getImageScale(rect: Rect): Float {
        return rect.width() * 1.0f / rect.height()
    }

    //获取ImageView 坐标
    fun getImageRect(iv: ImageView): Rect {
        val d = iv.drawable
        val result = Rect()
        iv.getGlobalVisibleRect(result)
        val tDrawableRect = d.bounds
        val drawableMatrix = iv.imageMatrix
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



    //计算小图与全屏大图时候的缩放度，用于起始动画
    fun getOriginalScale(){

    }

    fun startEnterAnim(
        target: View,
        rect: Rect,
        animatorListener: AnimatorListener?
    ) {
        val imgScale = getImageScale(rect)
        if (imgScale >= windowScale) {
//            animImgStartHeight = JWindowUtil.getWindowHeight(JApp.getIns()) * originalScale
//            pivotX = localX / (1 - originalScale)
//            pivotY = (localY - (animImgStartHeight - height) / 2) / (1 - originalScale)
        } else {
//            animImgStartWidth = JWindowUtil.getWindowWidth(JApp.getIns()) * originalScale
//            pivotX = (localX - (animImgStartWidth - width) / 2) / (1 - originalScale)
//            pivotY = localY / (1 - originalScale)
        }

    }
}