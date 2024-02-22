package com.example.githubuser.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.githubuser.ui.components.UserCard
import com.example.githubuser.ui.screen.home.HomeScreen
import com.example.githubuser.ui.theme.GithubUserTheme


@Composable
fun GithubUserApp() {
    Scaffold { innerPadding ->
       HomeScreen(modifier = Modifier.padding(innerPadding))
    }
}

@Preview(showBackground = true)
@Composable
fun GithubUserAppPreview() {
    GithubUserTheme {
        GithubUserApp()
    }
}
