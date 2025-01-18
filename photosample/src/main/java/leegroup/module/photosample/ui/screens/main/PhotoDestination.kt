package leegroup.module.photosample.ui.screens.main

import leegroup.module.compose.ui.models.BaseDestination
import leegroup.module.photosample.ui.models.PhotoUiModel

sealed class PhotoDestination {
    object PhotoRoot : BaseDestination("photoRoot")
    object PhotoList : BaseDestination("photoList")
}

internal typealias PhotoDetailNav = PhotoUiModel
