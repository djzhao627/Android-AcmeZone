package com.xayah.acmezone.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.xayah.acmezone.R

class ActivityBilibiliMainHelp : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bilibili_main_help)

        val bilibili_main_help_imageView_back:ImageView = findViewById(R.id.bilibili_main_help_imageView_back)
        bilibili_main_help_imageView_back.setOnClickListener { finish() }
    }
}