package leegroup.module.photosample.ui.screens.main.photodetail

internal sealed interface PhotoDetailAction {
    data object SwitchFavorite : PhotoDetailAction
}