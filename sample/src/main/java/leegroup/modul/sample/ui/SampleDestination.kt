package leegroup.modul.sample.ui

import leegroup.module.compose.ui.models.BaseDestination

sealed class SampleDestination {
    object SampleScreen : BaseDestination("sampleScreen")
}
