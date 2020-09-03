package com.starot.larger.act

import androidx.fragment.app.Fragment
import com.starot.larger.bean.LargerBean
import com.starot.larger.enums.LargerDataEnum
import com.starot.larger.fragment.ImageFg
import com.starot.larger.fragment.VideoFg

class CustomLargerAct : LargerAct<LargerBean>() {
    override fun createFragment(position: Int, data: LargerBean): Fragment {
        if (data.getType() == LargerDataEnum.IMAGE) {
            return ImageFg().newInstance(data, position)
        } else if (data.getType() == LargerDataEnum.Video) {
            return VideoFg().newInstance(data, position)
        }
        return Fragment()

    }
}