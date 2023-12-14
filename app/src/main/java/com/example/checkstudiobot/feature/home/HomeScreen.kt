package com.example.checkstudiobot.feature.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.example.checkstudiobot.R
import com.example.checkstudiobot.core.data.model.Review

@Composable
fun HomeContent(
    onReviewClicked: (Review) -> Unit,
    onPostButtonClicked: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = Unit) {
        viewModel.getReviews()
    }

    HomeScreen(
        uiState = uiState.value,
        onReviewClicked = onReviewClicked,
        onPostButtonClicked = onPostButtonClicked,
        onRetryButtonClicked = { viewModel.getReviews() },
        modifier = modifier,
    )
}

// Use GitHub Copilot to generate this function
@Composable
private fun HomeScreen(
    uiState: HomeUiState,
    onReviewClicked: (Review) -> Unit,
    onPostButtonClicked: () -> Unit,
    onRetryButtonClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    when (uiState) {
        is HomeUiState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(modifier = Modifier.size(48.dp))
            }
        }

        is HomeUiState.Success -> {
            LazyColumn {
                items(uiState.reviews) { review ->
                    ReviewItem(onClick = onReviewClicked, review = review, modifier = modifier)
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(end = 24.dp, bottom = 24.dp), contentAlignment = Alignment.BottomEnd
            ) {
                FloatingActionButton(onClick = onPostButtonClicked) {
                    Icon(Icons.Filled.Create, stringResource(id = R.string.post_review))
                }
            }
        }

        is HomeUiState.Error -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = stringResource(id = R.string.error_failure_loading))
                Button(onClick = onRetryButtonClicked) {
                    Text(text = stringResource(id = R.string.retry), color = Color.White)
                }
            }
        }
    }
}

@Composable
private fun ReviewItem(
    onClick: (Review) -> Unit,
    review: Review,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.clickable(true) {
            onClick(review)
        },
        verticalAlignment = Alignment.CenterVertically
    ) {
        SubcomposeAsyncImage(
            model = ImageRequest.Builder(LocalContext.current).data(review.imageUrls.firstOrNull())
                .build(),
            contentDescription = null,
            modifier = Modifier
                .clip(shape = RectangleShape)
                .weight(0.25f),
            loading = {
                CircularProgressIndicator(modifier = Modifier.size(48.dp))
            }
        )

        Spacer(modifier = Modifier.width(24.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(text = review.shopName, fontSize = 16.sp)
            Text(text = review.reviewTitle, fontSize = 12.sp)
            Text(text = review.reviewPoint.toString(), fontSize = 12.sp)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ReviewItemPreview() {
    HomeScreen(
        uiState = HomeUiState.Success(
            reviews = listOf(
                Review(
                    id = "1",
                    shopName = "Shop1",
                    reviewTitle = "Review1",
                    reviewBody = "ReviewBody1",
                    reviewPoint = 3.4f,
                    imageUrls = listOf("https://example.com"),
                ),
                Review(
                    id = "2",
                    shopName = "Shop2",
                    reviewTitle = "Review2",
                    reviewBody = "ReviewBody2",
                    reviewPoint = 3.4f,
                    imageUrls = listOf("https://example.com"),
                ),
                Review(
                    id = "3",
                    shopName = "Shop3",
                    reviewTitle = "Review3",
                    reviewBody = "ReviewBody3",
                    reviewPoint = 3.4f,
                    imageUrls = listOf("https://example.com"),
                ),
            )
        ),
        onReviewClicked = {},
        onPostButtonClicked = {},
        onRetryButtonClicked = {},
        modifier = Modifier.fillMaxSize(),
    )
}

@Preview(showBackground = true)
@Composable
fun ReviewErrorPreview() {
    HomeScreen(
        uiState = HomeUiState.Error,
        onReviewClicked = {},
        onPostButtonClicked = {},
        onRetryButtonClicked = {},
        modifier = Modifier.fillMaxSize(),
    )
}
