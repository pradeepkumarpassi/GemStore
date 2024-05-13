package com.example.gemstore

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class Recycleradapter3(private val dataList3: ArrayList<datamodel3>,
                       private val recyclerviewitemclicklistener2: Recyclerviewitemclicklistener2) :
    RecyclerView.Adapter<Recycleradapter3.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_layout_3, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemData = dataList3[position]

        holder.textViewName.text = itemData.name
        holder.textViewPrice.text = "Price: ${itemData.price}"

        // Load images using Glide
        Glide.with(holder.itemView.context)
            .load(itemData.imageUrl)
            .into(holder.imageView)

        holder.imageView.setOnClickListener{
            recyclerviewitemclicklistener2.onItemClicked2(position)
        }
        holder.textViewName.setOnClickListener{
            recyclerviewitemclicklistener2.onItemClicked2(position)
        }
    }

    override fun getItemCount(): Int {
        return dataList3.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.image4)
        val textViewName: TextView = itemView.findViewById(R.id.name4)
        val textViewPrice: TextView = itemView.findViewById(R.id.name5)
    }

}
