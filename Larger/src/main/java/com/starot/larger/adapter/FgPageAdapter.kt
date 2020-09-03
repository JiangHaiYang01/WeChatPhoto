package com.starot.larger.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter;

class FgPageAdapter<T>(
    fg: FragmentManager,
    lifecycle: Lifecycle,
    private val data: List<T>?,
    private val listener: OnCreateFragmentListener<T>
) :
    FragmentStateAdapter(fg, lifecycle) {
    override fun getItemCount(): Int {
        return data?.size ?: 0
    }

    override fun createFragment(position: Int): Fragment {
        if(data?.get(position) == null){
            return Fragment()
        }
        return listener.createFragment(position, data[position])
    }


    interface OnCreateFragmentListener<T> {
        fun createFragment(position: Int, data: T): Fragment
    }
}