package com.starot.wechat.bean

import com.starot.larger.bean.LargerBean
import com.starot.larger.enums.LargerDataEnum
import kotlinx.android.parcel.Parcelize

@Parcelize
class VideoBean : LargerBean() {
    override fun getType(): LargerDataEnum {
        return LargerDataEnum.Video
    }

}