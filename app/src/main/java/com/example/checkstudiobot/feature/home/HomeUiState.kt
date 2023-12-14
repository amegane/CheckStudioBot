package com.example.checkstudiobot.feature.home

import com.example.checkstudiobot.core.data.model.Review

sealed interface HomeUiState {
    data object Loading : HomeUiState

    data class Success(
        val reviews: List<Review>,
    ) : HomeUiState

    data object Error: HomeUiState
}