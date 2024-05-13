package com.example.gemstore

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import com.bumptech.glide.Glide

class ProfileImageDialog(context: Context, private val imageUrl: String) : Dialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_profile_image)

        val dialogImageView: ImageView = findViewById(R.id.dialogImageView)

        // Load the image into the ImageView using Glide
        Glide.with(context)
            .load(imageUrl)
            .into(dialogImageView)
    }

    // Adjust the dialog dimensions
    override fun onStart() {
        super.onStart()
        val width = ViewGroup.LayoutParams.MATCH_PARENT
        val height = ViewGroup.LayoutParams.WRAP_CONTENT
        window?.setLayout(width, height)
    }
}
