package com.frank.mulight.demo

import java.text.SimpleDateFormat
import java.util.*

object Utils {
    fun getFormatTime(time: Long): String {
        return SimpleDateFormat("yyyy-MM-dd HH:mm").format(Date(time))
    }
}