package com.xayah.acmezone.Activity

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.xayah.acmezone.R

class ActivityAboutApp : AppCompatActivity() {
    private lateinit var about_app_imageView_back: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_app)
        about_app_imageView_back = findViewById(R.id.about_app_imageView_back)
        about_app_imageView_back.setOnClickListener { finish() }

    }
}