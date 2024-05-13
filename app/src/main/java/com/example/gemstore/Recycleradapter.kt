package com.example.gemstore

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class Recycleradapter(
    private val context: Context,
    private val dataList: ArrayList<DataModel>
) : RecyclerView.Adapter<Recycleradapter.ViewHolder>() {

    // Define a listener interface
    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    // Store the listener
    private var onItemClickListener: OnItemClickListener? = null

    // Provide a method to set the listener
    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.onItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = dataList[position]

        // Set data to views
        Glide.with(context).load(data.imageResId).into(holder.imageView)
        holder.textView.text = data.Text

        // Set click listener
        holder.imageView.setOnClickListener {
            onItemClickListener?.onItemClick(position)
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.iv_image)
        val textView: TextView = itemView.findViewById(R.id.tv_description)
    }
}
