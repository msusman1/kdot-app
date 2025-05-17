package io.kdot.app.utils

import kotlinx.datetime.Instant
import kotlin.js.Date

actual fun Instant.formatDate(pattern: String, defValue: String): String {
    return try {
        val date = Date(this.toEpochMilliseconds())
        when (pattern) {
            "yyyy-MM-dd" -> "${date.getFullYear()}-${
                (date.getMonth() + 1).toString().padStart(2, '0')
            }-${date.getDate().toString().padStart(2, '0')}"

            "dd MMM yyyy" -> "${
                date.getDate().toString().padStart(2, '0')
            } ${monthName(date.getMonth())} ${date.getFullYear()}"

            "HH:mm" -> "${date.getHours().toString().padStart(2, '0')}:${
                date.getMinutes().toString().padStart(2, '0')
            }"

            else -> date.toLocaleString()
        }
    } catch (e: Exception) {
        defValue
    }
}

private fun monthName(month: Int): String {
    return listOf(
        "Jan", "Feb", "Mar", "Apr", "May", "Jun",
        "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
    ).getOrElse(month) { "???" }
}


actual fun String.parseDate(pattern: String, defValue: Long): Long {
    return try {
        when (pattern) {
            "yyyy-MM-dd" -> {
                val parts = this.split("-")
                if (parts.size == 3) {
                    val year = parts[0].toInt()
                    val month = parts[1].toInt() - 1
                    val day = parts[2].toInt()
                    Date(year, month, day).getTime().toLong()
                } else defValue
            }

            else -> {
                val timestamp = Date(this).getTime()
                if (timestamp.isNaN()) defValue else timestamp.toLong()
            }
        }
    } catch (e: Exception) {
        defValue
    }
}