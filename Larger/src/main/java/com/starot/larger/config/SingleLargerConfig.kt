package com.starot.larger.config

import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.starot.larger.impl.OnCustomItemViewListener

data class SingleLargerConfig(
    //列表
    var images: List<ImageView>? = null
) : BaseLargerConfig()