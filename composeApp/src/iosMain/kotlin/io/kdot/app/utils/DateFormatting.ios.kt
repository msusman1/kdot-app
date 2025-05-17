package io.kdot.app.utils

import kotlinx.datetime.Instant
import kotlinx.datetime.toNSDate
import platform.Foundation.NSDateFormatter

actual fun Instant.formatDate(pattern: String, defValue: String): String {
    return try {
        val dateFormatter = NSDateFormatter()
        dateFormatter.dateFormat = pattern
        dateFormatter.stringFromDate(
            toNSDate()
        )
    } catch (e: Exception) {
        defValue
    }

}

actual fun String.parseDate(pattern: String, defValue: Long): Long {
    return try {
        val dateFormatter = NSDateFormatter()
        dateFormatter.dateFormat = pattern
        val result = dateFormatter.dateFromString(this)?.timeIntervalSinceReferenceDate?.toLong()
        if (result != null) {
            result * 1000
        } else {
            defValue
        }
    } catch (e: Exception) {
        defValue
    }
}