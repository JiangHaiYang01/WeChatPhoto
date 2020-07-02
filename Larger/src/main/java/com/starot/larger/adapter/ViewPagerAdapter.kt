package com.starot.larger.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.starot.larger.R


class ViewPagerAdapter<T>(
    private val data: List<T>?,
    private val layoutId: Int,
    private val listener: OnBindViewHolderListener
) :
    RecyclerView.Adapter<ViewPagerAdapter.MyViewHolder>() {

    class MyViewHolder(inflate: View) : RecyclerView.ViewHolder(inflate)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
        )
    }

    override fun getItemCount(): Int {
        if (data != null) {
            return data.size
        }
        return 0
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        listener.onBindViewHolder(holder, position)
    }

    interface OnBindViewHolderListener {
        fun onBindViewHolder(holder: MyViewHolder, position: Int)
    }
}