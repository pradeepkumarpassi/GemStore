package com.example.gemstore

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView

class forget : AppCompatActivity() {

    lateinit var im1: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forget)

        im1=findViewById(R.id.imagwe7)

        im1.setOnClickListener {
            val intent = Intent(this,im1::class.java)
            startActivity(intent)
        }

    }
}