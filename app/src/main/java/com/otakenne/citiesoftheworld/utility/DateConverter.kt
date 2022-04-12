package com.otakenne.citiesoftheworld.utility

import java.text.SimpleDateFormat
import java.util.*

class DateConverter {
    companion object {
        fun makeDateReadable(date: Long): String {
            val l = System.currentTimeMillis()
            val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.UK)
            return simpleDateFormat.format(date)
        }
    }
}