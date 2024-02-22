package com.example.githubuser.ui.screen.home

import android.util.Log
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.githubuser.di.Injection
import com.example.githubuser.ui.ViewModelFactory
import com.example.githubuser.data.remote.Result
import com.example.githubuser.data.remote.response.UserListResponse
import com.example.githubuser.data.remote.response.UserListResponseItem
import com.example.githubuser.ui.components.LoadingIndicator
import com.example.githubuser.ui.components.UserCard
import com.example.githubuser.ui.screen.common.UiState

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getUsers()
                LoadingIndicator()
            }

            is UiState.Success -> {
                HomeContent(
                    users = uiState.data,
                    modifier = modifier,
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
    modifier: Modifier = Modifier,
) {
    LazyColumn() {
        if (users.isEmpty()) {
            item {
                Text(
                    text = "No data available",
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillParentMaxSize()
                )
            }
        } else {
            items(users, key = { it.id }) { user ->
                UserCard(
                    name = user.login,
                    followers = 0,
                    following = 0,
                    imageUrl = user.avatarUrl,
                    onClick = { /*TODO*/ }
                )
            }
        }
    }
}