package com.example.checkstudiobot.feature.review_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.checkstudiobot.core.data.repository.ReviewRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReviewDetailViewModel @Inject constructor(
    private val reviewRepository: ReviewRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val reviewId: String = checkNotNull(savedStateHandle["reviewId"])

    private val _reviewDetailUiState: MutableStateFlow<ReviewDetailUiState> =
        MutableStateFlow(ReviewDetailUiState.Loading)
    val reviewDetailUiState: StateFlow<ReviewDetailUiState> = _reviewDetailUiState.asStateFlow()

    init {
        viewModelScope.launch {
            try {
                val review = reviewRepository.getReview(reviewId = reviewId)
                if (review != null) {
                    _reviewDetailUiState.value = ReviewDetailUiState.Success(review)
                } else {
                    _reviewDetailUiState.value = ReviewDetailUiState.Error
                }
            } catch (e: Exception) {
                _reviewDetailUiState.value = ReviewDetailUiState.Error
            }
        }
    }
}
