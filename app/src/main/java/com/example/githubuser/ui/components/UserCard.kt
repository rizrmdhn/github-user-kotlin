package com.example.githubuser.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserCard(
    name: String,
    followers: Int,
    following: Int,
    imageUrl: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(8.dp)
        ) {
            SubcomposeAsyncImage(
                model = imageUrl,
                contentDescription =  name,
                modifier = Modifier
                    .size(64.dp)
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
                            .size(64.dp)
                            .clip(CircleShape)
                    )
                } else {
                    SubcomposeAsyncImageContent()
                }
            }
            Spacer(
                modifier = Modifier.size(8.dp)
            )
            Column {
                Text(
                    text = name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(
                    modifier = Modifier.size(8.dp)
                )
                Row {
                    Text(
                        text = "Followers: $followers",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Light
                    )
                    Spacer(
                        modifier = Modifier.size(8.dp)
                    )
                    Text(
                        text = "Following: $following",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Light
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun UserCardPreview() {
    UserCard(
        name = "John Doe",
        imageUrl = "https://avatars.githubusercontent.com/u/1?v=4",
        followers = 0,
        following = 0,
        onClick = {
            // do nothing
        }
    )
}
