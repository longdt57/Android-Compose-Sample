package leegroup.module.sample.gituser.ui.screens.gituser.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import leegroup.module.compose.ui.components.LoadMore
import leegroup.module.compose.ui.theme.ComposeTheme
import leegroup.module.domain.models.GitUserModel

@Composable
fun GitUserList(
    modifier: Modifier = Modifier,
    users: ImmutableList<GitUserModel>,
    onClick: (GitUserModel) -> Unit,
    onLoadMore: () -> Unit
) {
    val listState = rememberLazyListState()
    LoadMore(listState = listState, onLoadMore = onLoadMore)
    LazyColumn(
        modifier = modifier,
        state = listState,
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        item {
            Spacer(modifier = Modifier.height(8.dp))
        }
        items(users, key = { it.id }) { user ->
            GitUserListCard(user, onClick)
        }
        item {
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ScreenPreview() {
    ComposeTheme {
        GitUserList(
            users = persistentListOf(
                GitUserModel(
                    id = 1,
                    login = "longdt57",
                    avatarUrl = "https://avatars.githubusercontent.com/u/1?v=4",
                    htmlUrl = "https://github.com/longdt57"
                ),
                GitUserModel(
                    id = 2,
                    login = "defunkt",
                    avatarUrl = "https://avatars.githubusercontent.com/u/2?v=4",
                    htmlUrl = "https://github.com/defunkt"
                ),
                GitUserModel(
                    id = 3,
                    login = "pjhyett",
                    avatarUrl = "https://avatars.githubusercontent.com/u/3?v=4",
                    htmlUrl = "https://github.com/pjhyett"
                )
            ),
            onClick = {},
            onLoadMore = {}
        )
    }
}
