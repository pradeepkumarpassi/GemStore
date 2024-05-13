package com.example.gemstore

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class RecyclerViewAdapter12(
    private val context: Context,
    private val dataList2: List<Datamodel11>,
    private val onItemClick: (String) -> Unit
) : RecyclerView.Adapter<RecyclerViewAdapter12.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imagewomen)
        val textView: TextView = itemView.findViewById(R.id.nameofwomen)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_layout_11, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = dataList2[position]
        Picasso.get().load(currentItem.image).into(holder.imageView)
        holder.textView.text = currentItem.text

        holder.itemView.setOnClickListener {
            onItemClick(currentItem.text)
        }
    }

    override fun getItemCount(): Int {
        return dataList2.size
    }
}
