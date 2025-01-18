package leegroup.module.sample.gituser.ui.mapper.util

object FollowerFormatter {
    fun formatLargeNumber(value: Int, max: Int = 100): String {
        return if (value > max) "$max+" else value.toString()
    }
}