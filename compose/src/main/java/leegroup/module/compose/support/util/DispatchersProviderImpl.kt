package leegroup.module.compose.support.util

import kotlinx.coroutines.Dispatchers

object DispatchersProviderImpl : DispatchersProvider {

    override val io = Dispatchers.IO

    override val main = Dispatchers.Main

    override val default = Dispatchers.Default
}
