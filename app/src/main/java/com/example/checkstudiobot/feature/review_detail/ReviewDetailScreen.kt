package com.example.checkstudiobot.feature.review_detail

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.example.checkstudiobot.R
import com.example.checkstudiobot.core.data.model.Review

@Composable
fun ReviewDetailContent(
    modifier: Modifier = Modifier,
    viewModel: ReviewDetailViewModel = hiltViewModel(),
) {
    val uiState = viewModel.reviewDetailUiState.collectAsStateWithLifecycle()
    ReviewDetailScreen(uiState = uiState.value, modifier = modifier)
}

@Composable
private fun ReviewDetailScreen(
    uiState: ReviewDetailUiState,
    modifier: Modifier = Modifier,
) {
    when (uiState) {
        is ReviewDetailUiState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(modifier = Modifier.size(48.dp))
            }
        }

        is ReviewDetailUiState.Success -> {
            ReviewDetail(review = uiState.review, modifier = modifier)
        }

        is ReviewDetailUiState.Error -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = stringResource(id = R.string.error_failure_loading))
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ReviewDetail(review: Review, modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxSize()) {
        Text(text = review.shopName, style = MaterialTheme.typography.displayMedium)

        Text(text = review.reviewPoint.toString(), style = MaterialTheme.typography.titleMedium)

        Spacer(modifier = Modifier.height(12.dp))

        Text(text = review.reviewBody, style = MaterialTheme.typography.bodyMedium)

        Spacer(modifier = Modifier.height(24.dp))

        HorizontalPager(state = rememberPagerState(
            initialPage = 0,
            initialPageOffsetFraction = 0f
        ) {
            review.imageUrls.size
        }) { page ->
            SubcomposeAsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(review.imageUrls[page])
                    .build(),
                contentDescription = null,
                modifier = Modifier
                    .clip(shape = RectangleShape)
                    .fillMaxWidth(),
                loading = {
                    CircularProgressIndicator()
                }
            )
        }
    }
}

// Use GitHub Copilot to generate this code
@Preview
@Composable
fun ReviewDetailScreenPreview() {
    ReviewDetailScreen(
        uiState = ReviewDetailUiState.Success(
            review = Review(
                id = "1",
                shopName = "Shop",
                reviewTitle = "Review",
                reviewBody = "ReviewBodyReviewBodyReviewBodyReviewBodyReviewBodyReviewBodyReviewBodyReviewBodyReviewBodyReviewBodyReviewBodyReviewBodyReviewBodyReviewBodyReviewBodyReviewBodyReviewBody",
                reviewPoint = 3.4f,
                imageUrls = listOf(
                    "https://example.com",
                    "https://example.com",
                    "https://example.com",
                    "https://example.com",
                    "https://example.com"
                ),
            ),
        ),
    )
}