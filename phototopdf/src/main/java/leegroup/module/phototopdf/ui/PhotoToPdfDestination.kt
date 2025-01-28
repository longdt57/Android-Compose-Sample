package leegroup.module.phototopdf.ui

import leegroup.module.compose.ui.models.BaseDestination

sealed class PhotoToPdfDestination {
    object PhotoToPdfMainScreen : BaseDestination("PhotoToPdfMainScreen")
    object PhotoToPdfCreationScreen : BaseDestination("PhotoToPdfMainScreen")
    object PhotoToPdfDetailScreen : BaseDestination("PhotoToPdfDetailScreen")
}
