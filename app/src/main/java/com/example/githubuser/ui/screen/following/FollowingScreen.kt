package com.example.githubuser.ui.screen.following

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.githubuser.data.remote.response.UserListResponseItem
import com.example.githubuser.di.Injection
import com.example.githubuser.ui.ViewModelFactory
import com.example.githubuser.ui.common.UiState
import com.example.githubuser.ui.components.UserCard
import com.example.githubuser.ui.components.UserCardLoading

@Composable
fun FollowingScreen(
    username: String,
    viewModel: FollowingScreenViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideUserRepository())
    ),
    navigateToDetail: (String) -> Unit
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getFollowing(username)
                LazyColumn(
                    contentPadding = PaddingValues(
                        start = 20.dp,
                        end = 20.dp,
                        bottom = 16.dp
                    )
                ) {
                    items(10) {
                        UserCardLoading()
                    }
                }
            }

            is UiState.Success -> {
                FollowingScreenContent(
                    followerList = uiState.data,
                    navigateToDetail = {
                        navigateToDetail(it)
                    }
                )
            }

            is UiState.Error -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Error: $uiState")
                }
            }
        }
    }

}

@Composable
fun FollowingScreenContent(
    followerList: List<UserListResponseItem>,
    navigateToDetail: (String) -> Unit
) {
    val listState = rememberLazyListState()

    LazyColumn(
        state = listState,
        contentPadding = PaddingValues(
            start = 16.dp,
            end = 16.dp,
            bottom = 16.dp
        )
    ) {
        if (followerList.isEmpty()) {
            items(10) {
                UserCardLoading()
            }
        } else {
            items(followerList, key = { it.id }) { user ->
                UserCard(
                    name = user.login,
                    imageUrl = user.avatarUrl,
                    onClick = {
                        navigateToDetail(user.login)
                    }
                )
            }
        }
    }
}