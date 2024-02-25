package com.example.githubuser.ui.screen.favorite

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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.githubuser.data.local.entity.FavoriteUserEntity
import com.example.githubuser.di.Injection
import com.example.githubuser.ui.ViewModelFactory
import com.example.githubuser.ui.common.UiState
import com.example.githubuser.ui.components.Search
import com.example.githubuser.ui.components.UserCard

@Composable
fun FavoriteScreen(
    viewModel: FavoriteScreenViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideUserRepository(
                LocalContext.current
            ),
            Injection.provideSettingPreferences(
                LocalContext.current
            ),
        )

    ),
    navigateToDetail: (String) -> Unit,
    navigateToFavorite: () -> Unit,
    isDarkMode: Boolean,
    darkThemeSwitch: () -> Unit
) {
    val query by viewModel.query.collectAsState(initial = "")

    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getFavoriteUsers()
                LazyColumn(
                    contentPadding = PaddingValues(
                        start = 20.dp, end = 20.dp, bottom = 16.dp
                    ), modifier = Modifier.fillMaxSize()
                ) {
                    item {
                        Search(
                            query = "",
                            searchPlaceHolder = "Cari favorite user...",
                            onQueryChange = {},
                            onSearch = {},
                            active = false,
                            onActiveChange = {},
                            navigateToFavorite = {},
                            isDarkMode = isDarkMode,
                            darkModeSwitch = darkThemeSwitch,
                        )
                    }
                }
            }

            is UiState.Success -> {
                FavoriteContent(
                    users = uiState.data,
                    query = query,
                    onQueryChange = viewModel::searchFavoriteUsers,
                    navigateToDetail = navigateToDetail,
                    navigateToFavorite = navigateToFavorite,
                    isDarkMode = isDarkMode,
                    darkThemeSwitch = darkThemeSwitch
                )
            }

            is UiState.Error -> {}
        }

    }
}

@Composable
fun FavoriteContent(
    users: List<FavoriteUserEntity>,
    query: String,
    onQueryChange: (String) -> Unit,
    navigateToDetail: (String) -> Unit,
    navigateToFavorite: () -> Unit,
    isDarkMode: Boolean,
    darkThemeSwitch: () -> Unit
) {
    val listState = rememberLazyListState()

    LazyColumn(
        state = listState,
        contentPadding = PaddingValues(
            start = 20.dp, end = 20.dp, bottom = 16.dp
        ),
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            Search(
                query = query,
                searchPlaceHolder = "Cari favorite user...",
                onQueryChange = onQueryChange,
                onSearch = {},
                active = false,
                onActiveChange = {},
                navigateToFavorite = navigateToFavorite,
                isDarkMode = isDarkMode,
                darkModeSwitch = darkThemeSwitch,
            )
        }
        if (users.isEmpty()) {
            item {
                Text(
                    text = "No favorite user please add some!",
                    modifier = Modifier
                        .fillMaxSize(),
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center
                )
            }
        } else {
            items(users, key = { it.id }) { user ->
                UserCard(
                    id = user.id,
                    name = user.login,
                    imageUrl = user.avatarUrl,
                    onClick = {
                        navigateToDetail(user.login)
                    },
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FavoriteScreenPreview() {
    FavoriteContent(
        users = emptyList(),
        query = "",
        onQueryChange = {},
        navigateToDetail = {},
        navigateToFavorite = {},
        isDarkMode = true,
        darkThemeSwitch = {}
    )
}