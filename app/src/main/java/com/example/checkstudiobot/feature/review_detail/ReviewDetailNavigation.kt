package com.example.checkstudiobot.feature.review_detail

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.checkstudiobot.core.navigation.Route

fun NavGraphBuilder.reviewDetailScreen() {
    composable(route = Route.ReviewDetail.route, arguments = listOf(navArgument("reviewId") {
        type = NavType.StringType
        defaultValue = ""
    })) {
        ReviewDetailContent()
    }
}

fun NavHostController.navigateToReviewDetail(reviewId: String, navOptions: NavOptions? = null) {
    navigate(Route.ReviewDetail.createReviewDetailRoute(reviewId), navOptions)
}
