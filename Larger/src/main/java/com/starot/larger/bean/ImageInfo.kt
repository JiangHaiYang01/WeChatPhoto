package com.starot.larger.bean

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

//原来的小图片的 宽高 左上角信息
@Parcelize
data class ImageInfo(
    var height: Float = 0.0f,
    var width: Float = 0.0f,
    var left: Float = 0.0f,
    var top: Float = 0.0f
) : Parcelable