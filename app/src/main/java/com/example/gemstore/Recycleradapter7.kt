package com.example.gemstore

// BannerAdapter.kt
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class Recycleradapter7(private val context: Context, private val bannerList: List<datamodel7>) :
    RecyclerView.Adapter<Recycleradapter7.BannerViewHolder>() {

    inner class BannerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val bannerImage: ImageView = itemView.findViewById(R.id.banner6)

        fun bind(item:datamodel7) {
            Glide.with(context).load(item.imageUrl).into(bannerImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_layout_7, parent, false)
        return BannerViewHolder(view)
    }

    override fun onBindViewHolder(holder: BannerViewHolder, position: Int) {
        val item = bannerList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return bannerList.size
    }
}
