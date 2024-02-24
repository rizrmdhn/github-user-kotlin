package com.example.githubuser.ui.screen.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.example.githubuser.data.remote.response.DetailUserResponse
import com.example.githubuser.di.Injection
import com.example.githubuser.ui.ViewModelFactory
import com.example.githubuser.ui.common.UiState
import com.example.githubuser.ui.components.DetailPageLoader
import com.example.githubuser.ui.components.DetailUserTabLayout
import com.example.githubuser.ui.components.shimmerBrush
import com.example.githubuser.ui.screen.follower.FollowerScreen
import com.example.githubuser.ui.screen.following.FollowingScreen

@Composable
fun DetailScreen(
    username: String,
    viewModel: DetailScreenViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideUserRepository())
    ),
    navigateBack: () -> Unit,
    navigateToDetail: (String) -> Unit
) {

    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getUserDetails(username)
                DetailPageLoader()
            }

            is UiState.Success -> {
                DetailScreenContent(
                    userDetail = uiState.data,
                    navigateBack = navigateBack,
                    navigateToDetail = {
                        navigateToDetail(it)
                    }
                )
            }

            is UiState.Error -> {
            }
        }
    }
}


@Composable
fun DetailScreenContent(
    userDetail: DetailUserResponse,
    navigateBack: () -> Unit,
    navigateToDetail: (String) -> Unit
) {
    var tabIndex by remember {
        mutableIntStateOf(0)
    }

    val tabs = listOf("Followers", "Following")

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(
                bottom = 16.dp
            )
            .fillMaxWidth(),
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(
                onClick = {
                    navigateBack()
                },
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back"
                )
            }
        }
        Spacer(
            modifier = Modifier.size(16.dp)
        )
        SubcomposeAsyncImage(
            model = userDetail.avatarUrl,
            contentDescription = userDetail.login,
            modifier = Modifier
                .size(128.dp)
                .clip(CircleShape)
        ) {
            val state = painter.state
            if (state is AsyncImagePainter.State.Loading || state is AsyncImagePainter.State.Error) {
                Box(
                    modifier = Modifier
                        .background(
                            shimmerBrush(
                                targetValue = 1300f,
                                showShimmer = true
                            )
                        )
                        .size(128.dp)
                        .clip(CircleShape)
                )
            } else {
                SubcomposeAsyncImageContent()
            }
        }
        Spacer(
            modifier = Modifier.size(8.dp)
        )
        Text(
            text = userDetail.name,
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(
            modifier = Modifier
                .size(8.dp)
        )
        Text(
            text = "@${userDetail.login}",
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(
            modifier = Modifier
                .size(8.dp)
        )
        Row {
            Text(
                text = "Followers: ${userDetail.followers}",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(
                modifier = Modifier
                    .size(8.dp)
            )
            Text(
                text = "Following: ${userDetail.following}",
                style = MaterialTheme.typography.bodyMedium
            )
        }
        Spacer(
            modifier = Modifier.size(16.dp)
        )
        DetailUserTabLayout(
            tabIndex = tabIndex,
            tabs = tabs,
            onTabSelected = {
                tabIndex = it
            },
            mapFunction = {
                when (tabIndex) {
                    0 -> {
                        FollowerScreen(
                            username = userDetail.login,
                            navigateToDetail =  {
                                navigateToDetail(it)
                            }
                        )
                    }

                    1 -> {
                        FollowingScreen(
                            username = userDetail.login,
                            navigateToDetail = {
                                navigateToDetail(it)
                            }
                        )
                    }
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DetailScreenPreview() {
    DetailScreen(
        username = "username",
        navigateBack = {

            },
        navigateToDetail = {
            }
    )
}