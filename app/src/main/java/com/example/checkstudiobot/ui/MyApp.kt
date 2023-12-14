package com.example.checkstudiobot.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.checkstudiobot.core.navigation.Route
import com.example.checkstudiobot.navigation.AppNavHost

@Composable
fun App() {
    Scaffold {
        Surface(modifier = Modifier.padding(it), color = MaterialTheme.colorScheme.background) {
            AppNavHost(
                navHostController = rememberNavController(),
                startDestination = Route.Home.route,
            )
        }
    }
}