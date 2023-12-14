package com.example.checkstudiobot.feature.review_detail

import com.example.checkstudiobot.core.data.model.Review

sealed interface ReviewDetailUiState {
    data class Success(val review: Review) : ReviewDetailUiState
    data object Error : ReviewDetailUiState
    data object Loading : ReviewDetailUiState
}
