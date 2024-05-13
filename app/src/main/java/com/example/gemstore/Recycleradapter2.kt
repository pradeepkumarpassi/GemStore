package com.example.gemstore

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class Recycleradapter2(private val context: Context,
                       private val dataList2: List<DataModel2>,
                       private val recyclerViewItemClickListener: RecyclerViewItemClickListener) :
    RecyclerView.Adapter<Recycleradapter2.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout_2, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = dataList2[position]

        // Set data to views
        //  holder.imageView.setImageResource(data.imageResId)
        Glide.with(context).load(data.imageResId).into(holder.imageView)
        holder.textView.text = data.Text

        holder.imageView.setOnClickListener{
            recyclerViewItemClickListener.onItemClicked(position)
        }
    }


    override fun getItemCount(): Int {
        return dataList2.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.image1)
        val textView: TextView = itemView.findViewById(R.id.name1)
    }
}
