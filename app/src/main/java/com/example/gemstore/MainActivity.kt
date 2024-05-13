package com.example.gemstore

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button


class MainActivity : AppCompatActivity() {
    lateinit var btn1: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Check login status
        if (isLoggedIn()) {
            // User is logged in, open Home activity
            val intent = Intent(this, Home::class.java)
            startActivity(intent)
            finish() // Finish MainActivity so that it's not shown when pressing back from Home
        } else {
            // User is not logged in, open intro activity
            btn1 = findViewById(R.id.button2)
            btn1.setOnClickListener {
                val intent = Intent(this, intro::class.java)
                startActivity(intent)
            }
        }
    }

    private fun isLoggedIn(): Boolean {
        // Implement logic to check if the user is logged in
        // For example, you can check if a user ID is saved in SharedPreferences
        val sharedPreferences = getSharedPreferences("id", AppCompatActivity.MODE_PRIVATE)
        return sharedPreferences.getString("id", null) != null
    }
}