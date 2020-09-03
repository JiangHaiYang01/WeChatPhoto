package com.starot.larger.impl

import android.os.Parcelable
import com.starot.larger.enums.LargerDataEnum

/** 当前数据源的类型 **/
interface OnLargerType : Parcelable {

    fun getType(): LargerDataEnum
}