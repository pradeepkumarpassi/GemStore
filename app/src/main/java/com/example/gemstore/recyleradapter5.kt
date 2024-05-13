package com.example.gemstore

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class recyleradapter5(
    private val context: Context,
    private val imageList: ArrayList<Int>,
    private val titleList: ArrayList<String>,
    private val imageList2: ArrayList<Int>
) : BaseAdapter() {
    override fun getCount(): Int {
        return titleList.size
    }

    override fun getItem(position: Int): Any {
        return titleList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val itemView = inflater.inflate(R.layout.item_layout_5, null)

        val imageView = itemView.findViewById<ImageView>(R.id.imageView)
        val titleTextView = itemView.findViewById<TextView>(R.id.titleTextView)
        val arrowImageView = itemView.findViewById<ImageView>(R.id.arrowImageView)

        imageView.setImageResource(imageList[position])
        titleTextView.text = titleList[position]
        arrowImageView.setImageResource(imageList2[position])

        return itemView
    }
}
