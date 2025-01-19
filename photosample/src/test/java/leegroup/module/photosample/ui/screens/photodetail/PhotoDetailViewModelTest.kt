package leegroup.module.photosample.ui.screens.photodetail

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import leegroup.module.extension.JsonUtil
import leegroup.module.photosample.domain.params.SaveFavoriteParam
import leegroup.module.photosample.domain.usecases.photofavorite.SaveFavoriteUseCase
import leegroup.module.photosample.ui.screens.main.PhotoDetailNav
import leegroup.module.photosample.ui.screens.main.photodetail.PhotoDetailAction
import leegroup.module.photosample.ui.screens.main.photodetail.PhotoDetailViewModel
import leegroup.module.test.BaseUnitTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class PhotoDetailViewModelTest : BaseUnitTest() {

    private lateinit var viewModel: PhotoDetailViewModel
    private lateinit var savedStateHandle: SavedStateHandle
    private lateinit var saveFavoriteUseCase: SaveFavoriteUseCase

    private val photoDetailNav = PhotoDetailNav(
        id = 1,
        albumId = 101,
        thumbnailUrl = "https://example.com/thumbnail.jpg",
        title = "Sample Photo",
        url = "https://example.com/photo.jpg",
        isFavorite = false
    )


    @Before
    fun setUp() {
        savedStateHandle = SavedStateHandle(JsonUtil.encodeToMap(photoDetailNav))
        saveFavoriteUseCase = mockk(relaxed = true)

        viewModel = PhotoDetailViewModel(
            savedStateHandle = savedStateHandle,
            dispatchersProvider = testDispatcherProvider,
            saveFavoriteUseCase = saveFavoriteUseCase
        )
    }

    @Test
    fun `loadFromSavedStateHandle should populate uiModel correctly`() = runTest {
        viewModel.uiModel.test {
            val detailUiModel = expectMostRecentItem()
            assertEquals(photoDetailNav.id, detailUiModel.id)
            assertEquals(photoDetailNav.title, detailUiModel.title)
            assertEquals(photoDetailNav.url, detailUiModel.url)
            assertEquals(photoDetailNav.isFavorite, detailUiModel.isFavorite)
        }

    }

    @Test
    fun `handleAction with SwitchFavorite should toggle favorite state`() = runTest {
        // Arrange
        val initialUiModel = viewModel.uiModel.value
        assertFalse(initialUiModel.isFavorite)

        // Act
        viewModel.handleAction(PhotoDetailAction.SwitchFavorite)

        // Assert
        val updatedUiModel = viewModel.uiModel.value
        assertTrue(updatedUiModel.isFavorite)
        coVerify { saveFavoriteUseCase.invoke(SaveFavoriteParam(1, true)) }
    }

    @Test
    fun `handleAction with SwitchFavorite toggles back to non-favorite state`() = runTest {
        // Arrange
        viewModel.handleAction(PhotoDetailAction.SwitchFavorite)
        val updatedUiModel = viewModel.uiModel.value
        assertTrue(updatedUiModel.isFavorite)

        // Act
        viewModel.handleAction(PhotoDetailAction.SwitchFavorite)

        // Assert
        val toggledBackUiModel = viewModel.uiModel.value
        assertFalse(toggledBackUiModel.isFavorite)
        coVerify { saveFavoriteUseCase.invoke(SaveFavoriteParam(1, false)) }
    }
}
