package com.app.androidcompose.ui

import com.app.androidcompose.ui.base.BaseDestination

sealed class AppDestination {

    object RootNavGraph : BaseDestination("rootNavGraph")

    object MainNavGraph : BaseDestination("mainNavGraph")
}
