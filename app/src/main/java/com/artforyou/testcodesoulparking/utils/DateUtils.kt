package com.artforyou.testcodesoulparking.utils

import java.text.SimpleDateFormat
import java.util.Locale

object DateUtils {
    fun formatDate(dateStr: String, originalFormat: String = "yyyy-MM-dd HH:mm:ss", targetFormat: String = "dd MMM yyyy"): String? {
        return try {
            val originalFormatter = SimpleDateFormat(originalFormat, Locale.getDefault())
            val targetFormatter = SimpleDateFormat(targetFormat, Locale.getDefault())
            val date = originalFormatter.parse(dateStr)
            date?.let { targetFormatter.format(it) }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}