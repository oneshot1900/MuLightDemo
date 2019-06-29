package com.frank.mulight.demo

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import android.Manifest.permission.CAMERA
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment.DIRECTORY_PICTURES
import android.provider.MediaStore
import android.text.TextUtils
import android.widget.Toast
import com.frank.mulight.demo.database.ImageSqliteHelper
import com.frank.mulight.demo.entity.Image
import com.frank.mulight.demo.widget.EditDialog
import java.io.File


class MainActivity : AppCompatActivity(), View.OnClickListener {
    private val PERMISSIONS = Array(2) { WRITE_EXTERNAL_STORAGE;CAMERA }
    private val REQUEST_PERMISSION_CODE = 0
    private val REQUEST_CAPTURE_CODE = 1
    private val TMP_IMAGE_NAME = "tmp"
    private lateinit var IMAGE_PATH: String
    private lateinit var file: File
    private lateinit var sqliteHelper: ImageSqliteHelper
    private lateinit var editDialog: EditDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
        initView()
    }

    private fun initView() {
        findViewById<View>(R.id.btnTakePhoto).setOnClickListener(this)
        findViewById<View>(R.id.btnView).setOnClickListener(this)
    }

    private fun init() {
        IMAGE_PATH = getExternalFilesDir(DIRECTORY_PICTURES).absolutePath.apply {
            if (!endsWith(File.separator)) {
                IMAGE_PATH += File.separator
            }
        }
        file = File(IMAGE_PATH + TMP_IMAGE_NAME)
        sqliteHelper = ImageSqliteHelper(this)

        editDialog = EditDialog(this).apply {
            setRightClickListener(View.OnClickListener {
                val name = getInput()
                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(this@MainActivity, "name is blank!", Toast.LENGTH_LONG).show()
                } else {
                    dismiss()
                    saveImage(name!!)
                }
            })
            setLeftClickListener(View.OnClickListener {
                dismiss()
                deleteTmpImage()
            })
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btnTakePhoto -> {
                if (hasPermission()) {
                    takePhoto()
                } else {
                    requestPermission()
                }
            }
            R.id.btnView -> startActivity(Intent(this@MainActivity, ImageListActivity::class.java))
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CAPTURE_CODE) {
            if (file.exists()) {
                editDialog.setImage(BitmapFactory.decodeFile(file.absolutePath)).show()
            } else {
                Toast.makeText(this, "take photo failure", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_PERMISSION_CODE) {
            var allPermission = true
            for (grantResult in grantResults) {
                if (grantResult != PackageManager.PERMISSION_GRANTED) {
                    allPermission = false
                    break
                }
            }
            if (allPermission) {
                takePhoto()
            } else {
                Toast.makeText(this, "lack of permissions!", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun takePhoto() {
        val intent = Intent().apply {
            action = MediaStore.ACTION_IMAGE_CAPTURE
            addCategory(Intent.CATEGORY_DEFAULT)
            if (file.exists()) {
                file.delete()
            }
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file))
        }
        startActivityForResult(intent, REQUEST_CAPTURE_CODE)
    }

    private fun saveImage(name: String) {
        if (file.exists()) {
            file.renameTo(File(IMAGE_PATH + name))

            val image = Image().apply {
                this.name = name
                timeStamp = System.currentTimeMillis()
                path = IMAGE_PATH + name
            }
            sqliteHelper.insertEntity(image)

            Toast.makeText(this, "save success", Toast.LENGTH_LONG).show()
        }
    }

    private fun deleteTmpImage() {
        if (file.exists()) {
            file.delete()
        }
    }

    private fun requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(PERMISSIONS, REQUEST_CAPTURE_CODE)
        }
    }

    private fun hasPermission(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            (checkSelfPermission(PERMISSIONS[0]) == PackageManager.PERMISSION_GRANTED
                    && checkSelfPermission(PERMISSIONS[1]) == PackageManager.PERMISSION_GRANTED)
        } else {
            true
        }
    }


}
