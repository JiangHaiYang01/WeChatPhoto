package com.starot.larger.utils

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.FrameLayout
import androidx.core.view.ViewCompat

//将电池兰取消 沉寂式 的布局
object StatusBarTools {


    fun setStatusBar(activity: Activity) {
        translucentStatusBar(activity, true)
        changeToLightStatusBar(activity)
    }

    //SDK >= 21时, 取消状态栏的阴影
    private fun translucentStatusBar(
        activity: Activity,
        hideStatusBarBackground: Boolean
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            translucentStatusBarLOLLIPOP(activity, hideStatusBarBackground)
        } else
            translucentStatusBar(activity)
    }

    //SDK> = 23，将状态栏改为浅色模式（状态栏图标和字体会变成深色）
    private fun changeToLightStatusBar(activity: Activity) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return
        }
        val window = activity.window ?: return
        val decorView = window.decorView
        decorView.systemUiVisibility =
            decorView.systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }


    private fun translucentStatusBarLOLLIPOP(
        activity: Activity,
        hideStatusBarBackground: Boolean
    ) {
        val window = activity.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        if (hideStatusBarBackground) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = Color.TRANSPARENT
            window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        } else {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
        }
        val mContentView =
            window.findViewById<View>(Window.ID_ANDROID_CONTENT) as ViewGroup
        val mChildView = mContentView.getChildAt(0)
        if (mChildView != null) {
            ViewCompat.setFitsSystemWindows(mChildView, false)
            ViewCompat.requestApplyInsets(mChildView)
        }
    }

    private fun translucentStatusBar(activity: Activity) {
        val window = activity.window
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        val mContentView =
            activity.findViewById<View>(Window.ID_ANDROID_CONTENT) as ViewGroup
        val mContentChild = mContentView.getChildAt(0)
        removeFakeStatusBarViewIfExist(activity)
        removeMarginTopOfContentChild(
            mContentChild,
            getStatusBarHeight(activity)
        )
        if (mContentChild != null) {
            ViewCompat.setFitsSystemWindows(mContentChild, false)
        }
    }

    /**
     * return statusBar's Height in pixels
     */
    private fun getStatusBarHeight(context: Context): Int {
        var result = 0
        val resId =
            context.resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resId > 0) {
            result = context.resources.getDimensionPixelOffset(resId)
        }
        return result
    }


    /**
     * remove marginTop to simulate set FitsSystemWindow false
     */
    private fun removeMarginTopOfContentChild(
        mContentChild: View?,
        statusBarHeight: Int
    ) {
        if (mContentChild == null) {
            return
        }
        if ("marginAdded" == mContentChild.tag) {
            val lp =
                mContentChild.layoutParams as FrameLayout.LayoutParams
            lp.topMargin -= statusBarHeight
            mContentChild.layoutParams = lp
            mContentChild.tag = null
        }
    }


    /**
     * use reserved order to remove is more quickly.
     */
    private fun removeFakeStatusBarViewIfExist(activity: Activity) {
        val window = activity.window
        val mDecorView = window.decorView as ViewGroup
        val fakeView =
            mDecorView.findViewWithTag<View>("statusBarView")
        if (fakeView != null) {
            mDecorView.removeView(fakeView)
        }
    }


}