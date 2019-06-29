package com.frank.mulight.demo.widget

import android.app.Dialog
import android.content.Context
import android.graphics.Bitmap
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import com.frank.mulight.demo.R


class EditDialog(context: Context) : Dialog(context, R.style.Dialog) {
    private val et: EditText
    private val btnLeft: Button
    private val btnRight: Button
    private val imageView: ImageView

    init {
        setContentView(R.layout.dialog_edit)
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
        imageView.setImageResource(0)
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

    fun setImage(bitmap: Bitmap):EditDialog{
        imageView.setImageBitmap(bitmap)
        return this
    }
}