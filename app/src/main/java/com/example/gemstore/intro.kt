package com.example.gemstore

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class intro : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var indicatorLayout: LinearLayout
    private lateinit var indicatorViews: ArrayList<View>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        recyclerView = findViewById(R.id.recyclerView)
        indicatorLayout = findViewById(R.id.indicatorLayout)

        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.layoutManager = layoutManager
        val adapter = ImageAdapter(getSampleImageList())
        recyclerView.adapter = adapter

        // Initialize indicatorViews
        indicatorViews = ArrayList()
        setupIndicators(adapter.itemCount)

        // Scroll listener to update indicators
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
                updateIndicators(firstVisibleItemPosition)
            }
        })

        // Add your existing code here

        // Example: Open Signup Activity on Button Click
        val btn1: Button = findViewById(R.id.button33)
        btn1.setOnClickListener {
            val intent = Intent(this, loginin::class.java)
            startActivity(intent)
        }
    }

    private fun setupIndicators(numIndicators: Int) {
        indicatorLayout.removeAllViews()
        for (i in 0 until numIndicators) {
            val indicator = View(this)
            val size = resources.getDimensionPixelSize(R.dimen.indicator_size)
            val margin = resources.getDimensionPixelSize(R.dimen.indicator_margin)
            val params = LinearLayout.LayoutParams(size, size)
            params.setMargins(margin, 0, margin, 0)
            indicator.layoutParams = params
            indicator.setBackgroundResource(R.drawable.indicator_bg_inactive)
            indicatorLayout.addView(indicator)
            indicatorViews.add(indicator)
        }
        // Highlight the initial indicator
        if (indicatorViews.isNotEmpty()) {
            indicatorViews[0].setBackgroundResource(R.drawable.indicator_bg_active)
        }
    }

    private fun updateIndicators(currentPosition: Int) {
        for (i in indicatorViews.indices) {
            indicatorViews[i].setBackgroundResource(
                if (i == currentPosition) R.drawable.indicator_bg_active else R.drawable.indicator_bg_inactive
            )
        }
    }

    // Replace this with your actual image loading logic
    private fun getSampleImageList(): List<Int> {
        return listOf(
            R.drawable.fas2,
            R.drawable.fas3,
            R.drawable.fas1,
            // Add more image resource IDs
        )
    }
}
