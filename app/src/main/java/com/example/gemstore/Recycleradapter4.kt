package com.example.gemstore

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


class Recycleradapter4(private val context: Context, private val dataList4: List<Datamodel4>) :
    RecyclerView.Adapter<Recycleradapter4.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout_4, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = dataList4[position]

        // Set data to views
        Glide.with(context).load(data.image).into(holder.imageView)
    }

    override fun getItemCount(): Int {
        return dataList4.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.banner2)
    }
}
