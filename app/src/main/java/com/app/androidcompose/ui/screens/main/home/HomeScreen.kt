package com.app.androidcompose.ui.screens.main.home

import androidx.annotation.VisibleForTesting
import androidx.annotation.VisibleForTesting.Companion.PRIVATE
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.app.androidcompose.R
import com.app.androidcompose.data.model.User
import com.app.androidcompose.support.extensions.collectAsEffect
import com.app.androidcompose.ui.base.BaseDestination
import com.app.androidcompose.ui.base.BaseScreen
import com.app.androidcompose.ui.theme.ComposeTheme

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    navigator: (destination: BaseDestination) -> Unit,
) = BaseScreen(viewModel) {
    viewModel.navigator.collectAsEffect { destination -> navigator(destination) }

    val uiModel by viewModel.uiModels.collectAsStateWithLifecycle()

    HomeScreenContent(
        title = stringResource(id = R.string.app_name),
        uiModels = uiModel.users
    )
}

@Composable
private fun HomeScreenContent(
    title: String,
    uiModels: List<User>
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
    ) {
        Text(
            text = title,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 16.dp)
        )
        LazyVerticalGrid(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(uiModels) {
                UserContent(user = it)
            }
        }
    }
}

@Composable
@VisibleForTesting(otherwise = PRIVATE)
fun UserContent(user: User) {
    Text(
        text = "${user.firstName} ${user.lastName}",
        textAlign = TextAlign.Center,
    )
}

@Preview(showSystemUi = true)
@Composable
private fun HomeScreenPreview() {
    ComposeTheme {
        HomeScreenContent(
            title = stringResource(id = R.string.app_name),
            uiModels = listOf(
                User(
                    id = 1,
                    firstName = "Logan",
                    lastName = "Do"
                ),
                User(
                    id = 1,
                    firstName = "Logan",
                    lastName = "Do"
                ),
                User(
                    id = 1,
                    firstName = "Logan",
                    lastName = "Do"
                ), User(
                    id = 1,
                    firstName = "Logan",
                    lastName = "Do"
                )
            )
        )
    }
}
