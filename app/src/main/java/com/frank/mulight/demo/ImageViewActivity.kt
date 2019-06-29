package com.frank.mulight.demo

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.text.TextUtils
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class ImageViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_view)

        val imageView = findViewById<ImageView>(R.id.imageView).apply {
            setOnClickListener { finish() }
        }

        val path = intent.getStringExtra(KEY_PATH)
        if (!TextUtils.isEmpty(path)) {
            imageView.setImageBitmap(BitmapFactory.decodeFile(path))
        } else {
            finish()
        }
    }

    companion object {
        private val KEY_PATH = "KEY_PATH"
        fun startActivity(context: Context, path: String?) {
            val intent = Intent(context, ImageViewActivity::class.java).apply {
                putExtra(KEY_PATH, path)
            }
            context.startActivity(intent)
        }
    }
}