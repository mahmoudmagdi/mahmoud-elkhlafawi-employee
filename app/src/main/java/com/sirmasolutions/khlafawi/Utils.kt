package com.sirmasolutions.khlafawi

import android.annotation.SuppressLint
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
fun getDateFromString(dateStr: String?): Date? {

    var requiredDateStr = dateStr
    if (requiredDateStr == "NULL") {
        requiredDateStr = getCurrentDate()
    }

    val format = SimpleDateFormat("yyyy-MM-dd")
    try {
        requiredDateStr?.let {
            return format.parse(requiredDateStr)
        }
    } catch (e: ParseException) {
        e.printStackTrace()
    }

    return null
}

fun getCurrentDate(): String {
    val c = Calendar.getInstance().time
    val df = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return df.format(c)
}