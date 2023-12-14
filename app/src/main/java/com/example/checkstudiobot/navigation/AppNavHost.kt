package com.example.checkstudiobot.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.checkstudiobot.feature.home.homeScreen
import com.example.checkstudiobot.feature.post.navigateToPost
import com.example.checkstudiobot.feature.post.postScreen
import com.example.checkstudiobot.feature.review_detail.navigateToReviewDetail
import com.example.checkstudiobot.feature.review_detail.reviewDetailScreen

// Use Studio bot to generate this function
@Composable
fun AppNavHost(
    navHostController: NavHostController,
    startDestination: String,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navHostController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        homeScreen(
            onReviewClicked = { review ->
                navHostController.navigateToReviewDetail(review.id)
            },
            onPostButtonClicked = {
                navHostController.navigateToPost()
            }
        )
        reviewDetailScreen()
        postScreen { navHostController.popBackStack() }
    }
}