package com.frank.mulight.demo.widget

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.BitmapFactory
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import com.frank.mulight.demo.R
import java.io.File


class EditDialog(context: Context) : Dialog(context, R.style.Dialog) {
    private val et: EditText
    private val btnLeft: Button
    private val btnRight: Button
    private val imageView: ImageView

    init {
        setContentView(R.layout.dialog_edit)
        val attribute = WindowManager.LayoutParams().apply {
            val outMetrics = DisplayMetrics()
            (context as Activity).windowManager.defaultDisplay.getMetrics(outMetrics)
            val widthPixels = outMetrics.widthPixels
            val heightPixels = outMetrics.heightPixels

            width = (0.7 * widthPixels).toInt()
            height = (0.7 * heightPixels).toInt()

            window.setGravity(Gravity.CENTER)
        }
        window.attributes = attribute

        et = findViewById(R.id.et)
        btnLeft = findViewById(R.id.btnLeft)
        btnRight = findViewById(R.id.btnRight)
        imageView = findViewById(R.id.imageView)
        btnLeft.setOnClickListener {
            dismiss()
        }
        btnRight.setOnClickListener {
            dismiss()
        }
    }

    override fun show() {
        et.setText("")
        super.show()
    }

    fun setLeftClickListener(listener: View.OnClickListener): EditDialog {
        btnLeft.setOnClickListener(listener)
        return this
    }

    fun setRightClickListener(listener: View.OnClickListener): EditDialog {
        btnRight.setOnClickListener(listener)
        return this
    }

    fun getInput(): String? {
        when {
            et.text != null -> return et.text.toString()
            else -> return null
        }
    }

    fun setImageFile(file: File): EditDialog {
        imageView.setImageBitmap(BitmapFactory.decodeFile(file.absolutePath))
        return this
    }
}