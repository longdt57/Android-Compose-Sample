package com.app.androidcompose.ui.screens.main.gituserdetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.app.androidcompose.R
import com.app.androidcompose.support.extensions.collectAsEffect
import com.app.androidcompose.ui.base.BaseDestination
import com.app.androidcompose.ui.base.BaseScreen
import com.app.androidcompose.ui.models.GitUserDetailUiModel
import com.app.androidcompose.ui.screens.main.gituserdetail.components.GitUserDetailBlog
import com.app.androidcompose.ui.screens.main.gituserdetail.components.GitUserDetailCard
import com.app.androidcompose.ui.screens.main.gituserdetail.components.GitUserDetailFollows
import com.app.androidcompose.ui.theme.ComposeTheme

@Composable
fun GitUserDetailScreen(
    login: String,
    viewModel: GitUserDetailViewModel = hiltViewModel(),
    navigator: (destination: BaseDestination) -> Unit,
) = BaseScreen(viewModel) {
    viewModel.navigator.collectAsEffect { destination -> navigator(destination) }
    val uiModel by viewModel.uiModel.collectAsStateWithLifecycle()

    LaunchedEffect(login) {
        viewModel.handleAction(GitUserDetailAction.SetUserLogin(login))
    }

    GitUserDetailScreenContent(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .navigationBarsPadding()
            .statusBarsPadding(),
        uiModel = uiModel,
        onBack = { navigator(BaseDestination.Up()) }
    )
}

@Composable
private fun GitUserDetailScreenContent(
    modifier: Modifier = Modifier,
    uiModel: GitUserDetailUiModel,
    onBack: () -> Unit,
) {
    Column(modifier) {
        GitUserDetailAppBar(onBack = onBack)
        GitUserDetailCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .padding(top = 16.dp),
            name = uiModel.name,
            avatarUrl = uiModel.avatarUrl,
            location = uiModel.location
        )
        GitUserDetailFollows(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp)
                .padding(horizontal = 80.dp),
            followers = uiModel.followers,
            following = uiModel.following
        )
        GitUserDetailBlog(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .padding(top = 16.dp),
            blog = uiModel.blog
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun GitUserDetailAppBar(modifier: Modifier = Modifier, onBack: () -> Unit) {
    CenterAlignedTopAppBar(
        modifier = modifier,
        title = {
            Text(
                text = stringResource(id = R.string.git_user_detail_screen_title),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold
            )
        },
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(id = R.string.back)
                )
            }
        },
    )
}

@Preview(showSystemUi = true)
@Composable
private fun ScreenPreview() {
    ComposeTheme {
        GitUserDetailScreenContent(
            uiModel = GitUserDetailUiModel(
                name = "Logan Do",
                avatarUrl = "https://avatars.githubusercontent.com/u/8809113?v=4",
                blog = "https://github.com/longdt57",
                location = "Hanoi",
                followers = "100+",
                following = "50+",
            ),
            onBack = {}
        )
    }
}
