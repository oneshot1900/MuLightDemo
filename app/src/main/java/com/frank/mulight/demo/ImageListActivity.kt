package com.frank.mulight.demo

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.frank.mulight.demo.database.ImageSqliteHelper
import com.frank.mulight.demo.entity.Image
import java.io.File

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
        ImageSqliteHelper(this).queryList().let {
            val adapter = object : BaseQuickAdapter<Image, BaseViewHolder>(R.layout.item_image, it) {
                override fun convert(helper: BaseViewHolder, item: Image) {
                    helper.setText(R.id.tvName, item.name)
                    helper.setText(R.id.tvTimestamp, Utils.getFormatTime(item.timeStamp))
                    helper.setText(R.id.tvPath, item.path)
                    Glide.with(this@ImageListActivity).load(File(item.path)).into(helper.getView(R.id.imageView))
                }
            }.apply {
                onItemChildClickListener =
                    BaseQuickAdapter.OnItemChildClickListener { _, _, position ->
                        if (it != null) {
                            ImageViewActivity.startActivity(this@ImageListActivity, it[position].path)
                        }
                    }
            }
            recyclerView.adapter = adapter
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