package com.starot.larger.bean

import com.starot.larger.enums.LargerDataEnum
import com.starot.larger.impl.OnLargerType
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ImageBean(
    val thumbnailsUrl: String,
    val fullUrl: String
) : OnLargerType {
    override fun getType(): LargerDataEnum {
        return LargerDataEnum.IMAGE
    }
}