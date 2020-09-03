package com.starot.larger.bean

import com.starot.larger.enums.LargerDataEnum
import com.starot.larger.impl.OnLargerType
import kotlinx.android.parcel.Parcelize

abstract class LargerBean : OnLargerType {
    var thumbnailsUrl: String? = null
    var fullUrl: String? = null
}