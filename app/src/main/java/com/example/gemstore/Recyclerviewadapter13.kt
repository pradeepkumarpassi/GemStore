package com.example.gemstore

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class Recyclerviewadapter13(
    private val context: Context,
    private val productList: List<DataModel12>,
    private val recyclerViewitemclicklistener3: RecyclerViewitemclicklistener3
) :
    RecyclerView.Adapter<Recyclerviewadapter13.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout_12, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = productList[position]

        // Load images using Glide
        Glide.with(context).load(product.imageResourceId).into(holder.imageView1)

        // Set text values
        holder.textViewName.text = product.name
        holder.textViewDescription.text = product.description
        holder.textViewPrice.text = product.price
        holder.textViewmrp.text = product.mrp
        holder.textViewcompany.text = product.company

        holder.imageView1.setOnClickListener{
           recyclerViewitemclicklistener3.onItemClicked3(position)
        }
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView1: ImageView = itemView.findViewById(R.id.image1221)
        val textViewName: TextView = itemView.findViewById(R.id.textview1223)
        val textViewcompany: TextView = itemView.findViewById(R.id.textview1226)
        val textViewDescription: TextView = itemView.findViewById(R.id.textview1224)
        val textViewPrice: TextView = itemView.findViewById(R.id.textview1225)
        val textViewmrp: TextView = itemView.findViewById(R.id.textview1228)
    }
}
