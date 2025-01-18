package leegroup.module.sample.gituser

import leegroup.module.compose.ui.models.ErrorState
import java.net.UnknownHostException

object MockUtil {
    val noConnectivityException: Throwable = UnknownHostException()
    val apiErrorState = ErrorState.Api()
}