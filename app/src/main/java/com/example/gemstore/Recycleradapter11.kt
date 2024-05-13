package com.example.gemstore

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.squareup.picasso.Picasso

class Recycleradapter11(

    private val context: Context,
    private val dataList: List<Datamodel11>,
    private val onItemClick: (String) -> Unit,
    private val itemClickListener: RecyclerViewItemClickListener

) : RecyclerView.Adapter<Recycleradapter11.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imagewomen)
        val textView: TextView = itemView.findViewById(R.id.nameofwomen)
        val card: CardView = itemView.findViewById(R.id.cardview143)

    }
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
        val view = LayoutInflater.from(context).inflate(R.layout.item_layout_11, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = dataList[position]
        Picasso.get().load(currentItem.image).into(holder.imageView)
        holder.textView.text = currentItem.text

        holder.itemView.setOnClickListener {
            onItemClick(currentItem.text)
        }

        holder.card.setOnClickListener {
            itemClickListener.onItemClicked(position)
        }
    }



    override fun getItemCount(): Int {
        return dataList.size
    }
}
