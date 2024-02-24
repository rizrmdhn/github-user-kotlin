package com.example.githubuser.ui.screen.home

import android.util.Log
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.githubuser.di.Injection
import com.example.githubuser.ui.ViewModelFactory
import com.example.githubuser.data.remote.response.UserListResponseItem
import com.example.githubuser.ui.common.UiState
import com.example.githubuser.ui.components.Search
import com.example.githubuser.ui.components.UserCard
import com.example.githubuser.ui.components.UserCardLoading

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideUserRepository())
    ),
    navigateToDetail: (String) -> Unit
) {
    val query by viewModel.query.collectAsState(initial = "")

    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getUsers()
                LazyColumn(
                    contentPadding = PaddingValues(
                        start = 20.dp,
                        end = 20.dp,
                        bottom = 16.dp
                    ),
                    modifier = Modifier.fillMaxSize()
                ) {
                    item {
                        Search(
                            query = "",
                            onQueryChange = {},
                            onSearch = {},
                            active = false,
                            onActiveChange = {},
                        )
                    }
                    items(10) {
                        UserCardLoading()
                    }
                }
            }

            is UiState.Success -> {
                HomeContent(
                    users = uiState.data,
                    query = query,
                    onQueryChange = viewModel::searchUsers,
                    loadMore = {
                        viewModel.getMoreUsers()
                    },
                    navigateToDetail = navigateToDetail
                )
            }

            is UiState.Error -> {
                Text(
                    text = "Error: $uiState",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun HomeContent(
    users: List<UserListResponseItem>,
    loadMore: () -> Unit = {},
    query: String,
    onQueryChange: (String) -> Unit,
    moreItems: Boolean = true,
    navigateToDetail: (String) -> Unit
) {
    val listState = rememberLazyListState()

    LazyColumn(
        state = listState,
        contentPadding = PaddingValues(
            start = 20.dp,
            end = 20.dp,
            bottom = 16.dp
        )
    ) {
        item {
            Search(
                query = query,
                onQueryChange = onQueryChange,
                onSearch = {},
                active = false,
                onActiveChange = {},
            )
        }
        items(users, key = { it.id }) { user ->
            UserCard(
                name = user.login,
                imageUrl = user.avatarUrl,
                onClick = {
                    navigateToDetail(user.login)
                }
            )
        }
        items(users.size) { index ->
            if (index == users.size - 1) {
                if (moreItems) {
                    loadMore()
                    UserCardLoading()
                } else {
                    Text(
                        text = "No more items",
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillParentMaxSize()
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(
        navigateToDetail = {}
    )
}
