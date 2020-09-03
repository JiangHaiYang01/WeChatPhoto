package com.starot.larger.act

import androidx.fragment.app.Fragment
import com.starot.larger.bean.ImageBean
import com.starot.larger.fragment.ImageFg

class ImageLargerAct : LargerAct<ImageBean>() {
    override fun createFragment(position: Int, data: ImageBean): Fragment {
        return ImageFg().newInstance(data, position)
    }
}