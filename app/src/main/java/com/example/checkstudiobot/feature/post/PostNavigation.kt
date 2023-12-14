package com.example.checkstudiobot.feature.post

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.checkstudiobot.core.navigation.Route

fun NavGraphBuilder.postScreen(
    onPostButtonClicked: () -> Unit,
) {
    composable(route = Route.Post.route) {
        PostContent(
            onPostButtonClicked = onPostButtonClicked,
        )
    }
}

fun NavHostController.navigateToPost(navOptions: NavOptions? = null) {
    navigate(Route.Post.route, navOptions)
}