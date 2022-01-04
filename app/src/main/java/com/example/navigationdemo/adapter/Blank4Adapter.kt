package com.example.navigationdemo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.navigationdemo.R
import com.example.navigationdemo.bean.Blank4Bean
import com.zhangteng.base.base.BaseAdapter

class Blank4Adapter(data: MutableList<Blank4Bean?>?) :
    BaseAdapter<Blank4Bean, BaseAdapter.DefaultViewHolder>(data) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DefaultViewHolder {
        return DefaultViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_blank4, parent, false)
        )
    }

    override fun onBindViewHolder(holder: DefaultViewHolder, item: Blank4Bean?, position: Int) {
        holder.setText(R.id.textView, position.toString())
    }
}