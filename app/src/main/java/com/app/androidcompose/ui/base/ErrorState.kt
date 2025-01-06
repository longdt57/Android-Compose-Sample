package com.app.androidcompose.ui.base

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.app.androidcompose.R

sealed interface ErrorState {

    data object None : ErrorState

    interface MessageError : ErrorState {
        val errorCode: Int? get() = null
        val icon: (@Composable () -> Unit)? get() = null
        val title: (@Composable () -> String) get() = { stringResource(R.string.popup_error_unknown_title) }
        val message: (@Composable () -> String) get() = { stringResource(R.string.popup_error_unknown_body) }
        val confirmText: (@Composable () -> String) get() = { stringResource(R.string.common_close) }
        val dismissText: (@Composable () -> String)? get() = null
    }

    data object Common : MessageError

    data class Network(
        override val errorCode: Int? = null,
        override val icon: (@Composable () -> Unit)? = null,
        override val title: (@Composable () -> String) = { stringResource(R.string.popup_error_no_connection_title) },
        override val message: (@Composable () -> String) = { stringResource(R.string.popup_error_no_connection_body) },
        override val confirmText: (@Composable () -> String) = { stringResource(R.string.common_retry) },
        override val dismissText: (@Composable () -> String)? = { stringResource(R.string.common_close) }
    ) : MessageError

    data class Api(
        override val errorCode: Int? = null,
        override val icon: (@Composable () -> Unit)? = null,
        override val title: (@Composable () -> String) = { stringResource(R.string.popup_error_unknown_title) },
        override val message: (@Composable () -> String) = { stringResource(R.string.popup_error_unknown_body) },
        override val confirmText: (@Composable () -> String) = { stringResource(R.string.common_retry) },
        override val dismissText: (@Composable () -> String)? = { stringResource(R.string.common_close) },
        val customMessage: String?
    ) : MessageError

    data class Server(
        override val errorCode: Int? = null,
        override val icon: (@Composable () -> Unit)? = null,
        override val title: (@Composable () -> String) = { stringResource(R.string.popup_error_timeout_title) },
        override val message: (@Composable () -> String) = { stringResource(R.string.popup_error_timeout_body) },
        override val confirmText: (@Composable () -> String) = { stringResource(R.string.common_close) },
        override val dismissText: (@Composable () -> String)? = null,
    ) : MessageError
}
