package com.example.checkstudiobot.core.navigation

sealed class Route(val route: String) {
    data object Home : Route("home")
    data object ReviewDetail : Route("review_detail/{reviewId}") {
        fun createReviewDetailRoute(reviewId: String): String {
            return "review_detail/$reviewId"
        }
    }

    data object Post : Route("post")
}