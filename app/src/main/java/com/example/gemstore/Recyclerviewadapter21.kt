package com.example.gemstore

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class Recyclerviewadapter21(private val context: Context, private var dataList1: MutableList<Datamodle21>,private val deletebutton: deletebutton) :
    RecyclerView.Adapter<Recyclerviewadapter21.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layoutaddtocartitems, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = dataList1[position]

        Glide.with(context).load(data.image).into(holder.imageView1)

        holder.textViewName.text = data.name
        holder.textViewSaving.text = data.saving
        holder.textViewPrice.text = data.price
        holder.textViewMrp.text = data.mrp
        holder.textViewcompany.text = data.company

        holder.deleteViewbutton.setOnClickListener {
            deletebutton.onItemClicked4(position)
        }
    }

    override fun getItemCount(): Int {
        return dataList1.size
    }

    // Method to update the data in the adapter
    fun setData(newDataList: List<Datamodle21>) {
        dataList1.clear()
        dataList1.addAll(newDataList)
        notifyDataSetChanged() // Notify RecyclerView that data has changed
    }

    // Method to get a specific item from the data list
    fun getItem(position: Int): Datamodle21 {
        return dataList1[position]
    }

    // Method to update the price of a specific item in the data list
    fun updatePrice(position: Int, newPrice: String) {
        dataList1[position] = dataList1[position].copy(price = newPrice)
        notifyItemChanged(position) // Notify RecyclerView that item has changed
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView1: ImageView = itemView.findViewById(R.id.image1221)
        val textViewName: TextView = itemView.findViewById(R.id.textview1223)
        val textViewSaving : TextView = itemView.findViewById(R.id.textview1224)
        val textViewPrice: TextView = itemView.findViewById(R.id.textview1225)
        val textViewMrp: TextView = itemView.findViewById(R.id.textview1226)
        val textViewcompany: TextView = itemView.findViewById(R.id.textview1227)

        val deleteViewbutton: Button = itemView.findViewById(R.id.deletebutton12345)
    }
}
