package com.example.githubuser.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.githubuser.R
import com.example.githubuser.ui.theme.GithubUserTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Search(
    query: String,
    searchPlaceHolder: String,
    onQueryChange: (String) -> Unit,
    onSearch: (String) -> Unit,
    active: Boolean,
    onActiveChange: (Boolean) -> Unit,
    navigateToFavorite: () -> Unit,
    isDarkMode: Boolean,
    darkModeSwitch: () -> Unit,
    modifier: Modifier = Modifier
) {
    SearchBar(
        query = query,
        onQueryChange = onQueryChange,
        onSearch = onSearch,
        active = active,
        onActiveChange = onActiveChange,
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface
            )
        },
        placeholder = {
            Text(
                text = searchPlaceHolder,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        },
        trailingIcon = {
            Row {
                IconButton(
                    onClick = {
                        navigateToFavorite()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
                IconButton(
                    onClick = {
                        darkModeSwitch()
                    }
                ) {
                    Icon(
                        painter = painterResource(
                            if (isDarkMode) R.drawable.baseline_dark_mode_24
                            else R.drawable.baseline_sunny_24
                        ),
                        contentDescription = null,
                    )
                }
            }
        },
        content = {
        },
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
            .heightIn(
                min = 56.dp
            )
    )
}

@Preview(showBackground = true)
@Composable
fun SearchPreview() {
    GithubUserTheme {
        Search(
            query = "",
            searchPlaceHolder = "Cari favorite user...",
            onQueryChange = {},
            onSearch = {},
            active = false,
            onActiveChange = {},
            navigateToFavorite = {},
            isDarkMode = true,
            darkModeSwitch = {}
        )
    }
}
