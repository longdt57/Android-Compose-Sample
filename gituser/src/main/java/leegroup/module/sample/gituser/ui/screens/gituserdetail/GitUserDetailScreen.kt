package leegroup.module.sample.gituser.ui.screens.gituserdetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import leegroup.module.compose.support.extensions.collectAsEffect
import leegroup.module.compose.ui.components.BaseScreen
import leegroup.module.compose.ui.models.BaseDestination
import leegroup.module.compose.ui.theme.ComposeTheme
import leegroup.module.sample.gituser.ui.models.GitUserDetailUiModel
import leegroup.module.sample.gituser.ui.screens.gituserdetail.components.GitUserDetailAppBar
import leegroup.module.sample.gituser.ui.screens.gituserdetail.components.GitUserDetailBlog
import leegroup.module.sample.gituser.ui.screens.gituserdetail.components.GitUserDetailCard
import leegroup.module.sample.gituser.ui.screens.gituserdetail.components.GitUserDetailFollows

@Composable
internal fun GitUserDetailScreen(
    viewModel: GitUserDetailViewModel = hiltViewModel(),
    navigator: (destination: Any) -> Unit,
) = BaseScreen(viewModel) {
    viewModel.navigator.collectAsEffect { destination -> navigator(destination) }
    val uiModel by viewModel.uiModel.collectAsStateWithLifecycle()

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
