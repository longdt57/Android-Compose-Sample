package com.example.note.support.utils

import leegroup.module.compose.support.util.DatetimeUtil
import java.util.TimeZone

internal object DatetimeUtil {

    fun formatDateTime(
        timestamp: Long,
        timeZone: TimeZone = TimeZone.getDefault()
    ): String {
        return DatetimeUtil.formatDateTime(timestamp, timeZone)
    }
}