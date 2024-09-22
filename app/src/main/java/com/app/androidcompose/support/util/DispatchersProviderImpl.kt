package com.app.androidcompose.support.util

import javax.inject.Inject
import kotlinx.coroutines.Dispatchers

class DispatchersProviderImpl @Inject constructor() : DispatchersProvider {

    override val io = Dispatchers.IO

    override val main = Dispatchers.Main

    override val default = Dispatchers.Default
}
