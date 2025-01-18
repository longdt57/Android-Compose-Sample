package leegroup.module.photosample.ui.screens.main.photodetail

sealed interface PhotoDetailAction {
    data object SwitchFavorite : PhotoDetailAction
}