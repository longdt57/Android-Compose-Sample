package leegroup.module.compose.ui.models

enum class ScreenSize {
    SMALL,    // Small devices (e.g., phones in portrait mode)
    MEDIUM,   // Medium devices (e.g., tablets or phones in landscape mode)
    LARGE;    // Large devices (e.g., large tablets or wide phones)

    companion object {
        operator fun invoke(widthDp: Int): ScreenSize {
            return when {
                widthDp <= 600 -> SMALL
                widthDp <= 900 -> MEDIUM
                else -> LARGE
            }
        }
    }
}