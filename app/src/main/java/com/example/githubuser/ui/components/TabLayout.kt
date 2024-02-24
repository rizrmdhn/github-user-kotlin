package com.example.githubuser.ui.components

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun DetailUserTabLayout(
    tabIndex: Int,
    tabs: List<String>,
    onTabSelected: (Int) -> Unit = {},
    mapFunction: @Composable () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxSize()

    ) {
        TabRow(
            selectedTabIndex = tabIndex,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[tabIndex]),
                    color = MaterialTheme.colorScheme.primary
                )
            }
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    text = {
                        Text(title)
                    },
                    selected = index == tabIndex,
                    onClick = {
                        onTabSelected(index)
                    }
                )
            }
        }
        mapFunction()
    }
}

@Preview(showBackground = true)
@Composable
fun DetailUserTabLayoutPreview() {
    DetailUserTabLayout(
        tabIndex = 1,
        tabs = listOf("Tab 1", "Tab 2", "Tab 3"),
        mapFunction = {
            Text("Map Function")
        }

    )
}



