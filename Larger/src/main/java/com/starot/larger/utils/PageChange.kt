package com.starot.larger.utils

import androidx.viewpager2.widget.ViewPager2

class PageChange {


    fun register(viewPager2: ViewPager2, listener: PageChangeListener) {
        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                listener.onPageChange(position)
            }
        })
    }

    interface PageChangeListener {
        fun onPageChange(pos: Int)
    }
}