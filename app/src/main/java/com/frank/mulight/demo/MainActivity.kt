package com.frank.mulight.demo

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<View>(R.id.btnTakePhoto).setOnClickListener(this)
        findViewById<View>(R.id.btnView).setOnClickListener(this)

    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.btnTakePhoto->{}
            R.id.btnView->startActivity(Intent(this@MainActivity,ImageListActivity::class.java))
        }
    }
}
