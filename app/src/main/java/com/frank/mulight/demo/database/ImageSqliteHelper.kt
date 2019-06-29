package com.frank.mulight.demo.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.text.TextUtils
import com.frank.mulight.demo.entity.Image
import kotlin.collections.ArrayList

const val DB_NAME = "image.db"
const val TABLE_NAME = "image_table"
const val DB_VERSION = 1
const val ID = "id"
const val NAME = "name"
const val TIMESTAMP = "timeStamp"
const val PATH = "path"
const val ORDER = "$TIMESTAMP desc"

/**
 * Created by heyunfei on 2019/6/28.
 */
class ImageSqliteHelper constructor(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            "create table if not exists " +
                    TABLE_NAME +
                    "(" + ID + " integer primary key autoincrement, " +
                    NAME + " text, " +
                    TIMESTAMP + " long, " +
                    PATH + " text" + ")"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun insertEntity(image: Image): Long {
        if (TextUtils.isEmpty(image.name) || TextUtils.isEmpty(image.path)) {
            return -1
        }
        val contentValues = ContentValues().apply {
            put(NAME, image.name)
            put(TIMESTAMP, image.timeStamp)
            put(PATH, image.path)
        }
        val db = writableDatabase
        val resultId = db.insert(TABLE_NAME, null, contentValues)
        db.close()
        return resultId
    }

    fun queryList(): List<Image>? {
        val db = readableDatabase
        val cursor = db.query(TABLE_NAME, null, null, null, null, null, ORDER)
        var list = ArrayList<Image>()
        while (cursor.moveToNext()) {
            val image = Image().apply {
                name = cursor.getString(cursor.getColumnIndex(NAME))
                timeStamp = cursor.getLong(cursor.getColumnIndex(TIMESTAMP))
                path = cursor.getString(cursor.getColumnIndex(PATH))
            }
            list.add(image)
        }
        cursor.close()
        db.close()
        return list
    }
}