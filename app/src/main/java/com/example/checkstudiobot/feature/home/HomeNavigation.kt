package com.example.checkstudiobot.feature.home

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.checkstudiobot.core.data.model.Review
import com.example.checkstudiobot.core.navigation.Route

fun NavGraphBuilder.homeScreen(
    onReviewClicked: (Review) -> Unit,
    onPostButtonClicked: () -> Unit,
) {
    composable(route = Route.Home.route) {
        HomeContent(
            onReviewClicked = onReviewClicked,
            onPostButtonClicked =  onPostButtonClicked,
        )
    }
}

fun NavHostController.navigateToHome(navOptions: NavOptions? = null) {
    navigate(Route.Home.route, navOptions)
}