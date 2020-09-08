package com.starot.larger.impl

import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.starot.larger.Larger
import com.starot.larger.config.DefConfig
import com.starot.larger.enums.LargerEnum

//处理返回一些 框架需要的数据
interface OnLargerListener : OnLargerConfigListener {

    //返回larger 界面的 需要加载的 大图 id
    fun getFullViewId(): Int

    //获取layout id
    fun getLayoutId(): Int


}