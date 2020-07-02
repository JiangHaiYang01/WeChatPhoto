package com.starot.larger.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.starot.larger.R
import com.starot.larger.adapter.ViewPagerAdapter
import kotlinx.android.synthetic.main.activity_larger_base.*


abstract class LargerAct<T> : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        overridePendingTransition(0, 0)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_larger_base)
        setAdapter()
    }

    private fun setAdapter() {
        val data = getData()
        viewpager.adapter = ViewPagerAdapter(
            data,
            getItemLayout(),
            object : ViewPagerAdapter.OnBindViewHolderListener {
                override fun onBindViewHolder(
                    holder: ViewPagerAdapter.MyViewHolder,
                    position: Int
                ) {
                    val itemView = holder.itemView
                    if (data == null) {
                        item(itemView, position, null)
                    } else {
                        item(itemView, position, data[position])
                    }

                }
            })
    }

    abstract fun getItemLayout(): Int

    abstract fun getData(): List<T>?

    abstract fun item(itemView: View, position: Int, data: T?)
}
