package leegroup.module.phototopdf.support.utils

import leegroup.module.compose.support.util.DatetimeUtil

object DateTimeUtils {

    fun getCurrentDateTimeString(): String {
        return DatetimeUtil.formatDateTime()
    }
}