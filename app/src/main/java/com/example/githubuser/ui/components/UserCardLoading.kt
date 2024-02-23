package com.example.githubuser.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
fun UserCardLoading(
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(
                        shimmerBrush(
                            targetValue = 1300f,
                            showShimmer = true
                        )
                    )
            )
            Spacer(
                modifier = Modifier.size(8.dp)
            )
            Column {
                Box(
                    modifier = Modifier
                        .background(
                            shimmerBrush(
                                targetValue = 1300f,
                                showShimmer = true
                            )
                        )
                        .width(100.dp)
                        .height(16.dp)
                )
                Spacer(
                    modifier = Modifier.size(8.dp)
                )
                Row {
                    Box(
                        modifier = Modifier
                            .background(
                                shimmerBrush(
                                    targetValue = 1300f,
                                    showShimmer = true
                                )
                            )
                            .width(100.dp)
                            .height(16.dp)
                    )
                    Spacer(
                        modifier = Modifier.size(8.dp)
                    )
                    Box(
                        modifier = Modifier
                            .background(
                                shimmerBrush(
                                    targetValue = 1300f,
                                    showShimmer = true
                                )
                            )
                            .width(100.dp)
                            .height(16.dp)
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun UserCardLoadingPreview() {
    UserCardLoading()
}
