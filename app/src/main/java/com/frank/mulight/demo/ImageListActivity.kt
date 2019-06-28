package com.frank.mulight.demo

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.frank.mulight.demo.database.ImageSqliteHelper
import com.frank.mulight.demo.entity.Image

/**
 * Created by heyunfei on 2019/6/28.
 */
class ImageListActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_list)
        initView()
        loadImageList()
    }

    private fun loadImageList() {
        val list=ArrayList<Image>()
        for(int in 0..7){
            list.add(Image())
        }
        ImageSqliteHelper(this).apply {
            recyclerView.adapter = object : BaseQuickAdapter<Image, BaseViewHolder>(R.layout.item_image,list) {
                override fun convert(helper: BaseViewHolder, item: Image) {

                }
            }
        }
    }

    private fun initView() {
        findViewById<View>(R.id.ivBack).setOnClickListener(this)
        recyclerView = findViewById<RecyclerView>(R.id.recyclerView).apply {
            layoutManager = GridLayoutManager(
                this@ImageListActivity,
                4
            )
        }
    }

    override fun onClick(v: View?) {
        finish()
    }

}