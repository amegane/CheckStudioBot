package com.example.checkstudiobot.feature.home

import android.util.Log
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
class HomeViewModel @Inject constructor(
    private val reviewRepository: ReviewRepository,
) : ViewModel() {
    private val _uiState: MutableStateFlow<HomeUiState> = MutableStateFlow(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    fun getReviews() {
        _uiState.value = HomeUiState.Loading
        viewModelScope.launch {
            try {
                val reviews = reviewRepository.getReviews()
                _uiState.value = HomeUiState.Success(reviews)
            } catch (e: Exception) {
                Log.e("HomeViewModel", e.toString())
                _uiState.value = HomeUiState.Error
            }
        }
    }
}
