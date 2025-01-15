package leegroup.module.compose.ui.viewmodel

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

@Suppress("PropertyName", "MemberVisibilityCanBePrivate")
abstract class SavedStateBaseViewModel<T : Parcelable>(
    private val savedStateHandle: SavedStateHandle,
    initialUiState: T
) : BaseViewModel() {

    private val uiStateKey = "${KEY_UI_STATE}_${initialUiState::class.simpleName.orEmpty()}"

    // Retrieve the persisted UI state from SavedStateHandle
    protected val _uiState: MutableStateFlow<T> =
        MutableStateFlow(savedStateHandle.get<T>(uiStateKey) ?: initialUiState)
    val uiState: StateFlow<T> = _uiState

    protected open fun update(function: (T) -> T) {
        _uiState.update(function)
    }

    protected open fun updateAndSave(function: (T) -> T) {
        val currentValue = getUiState()
        val newValue = function(currentValue)

        if (currentValue != newValue) {
            _uiState.value = newValue
            savedStateHandle[uiStateKey] = newValue
        }
    }

    protected fun getUiState() = _uiState.value

    companion object {
        private const val KEY_UI_STATE = "uiState"
    }
}
