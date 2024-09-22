package com.app.androidcompose.ui.screens.main

import com.app.androidcompose.ui.base.BaseDestination

sealed class MainDestination {

    object Home : BaseDestination("home")
}
