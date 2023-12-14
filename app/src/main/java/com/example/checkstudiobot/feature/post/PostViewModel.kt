package com.example.checkstudiobot.feature.post

import android.util.Log
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.checkstudiobot.core.data.model.Review
import com.example.checkstudiobot.core.data.repository.ReviewRepository
import com.example.checkstudiobot.core.domain.AuthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    private val authUseCase: AuthUseCase,
    private val reviewRepository: ReviewRepository,
) : ViewModel() {
    private val _loginState = MutableStateFlow<LoginState>(LoginState.LoggedOut)
    val loginState = _loginState.asStateFlow()

    private val _postState = MutableStateFlow<PostState>(PostState.Default)
    val postState = _postState.asStateFlow()

    fun onSignIn(launcher: ActivityResultLauncher<IntentSenderRequest>) {
        viewModelScope.launch {
            try {
                authUseCase.signIn(launcher)
                _loginState.value = LoginState.LoggedIn
            } catch (e: Exception) {
                Log.e("PostViewModel", "onSignIn: ", e)
                _loginState.value = LoginState.LoggedOut
            }
        }
    }

    fun onResultSignIn(result: ActivityResult) {
        viewModelScope.launch {
            try {
                authUseCase.signInResult(result)
                _loginState.value = LoginState.LoggedIn
            } catch (e: Exception) {
                Log.e("PostViewModel", "onResultSignIn: ", e)
                _loginState.value = LoginState.LoggedOut
            }
        }
    }

    fun onPostButtonClicked(review: Review) {
        _postState.value = PostState.Posting
        viewModelScope.launch {
            try {
                reviewRepository.postReview(review)
                _postState.value = PostState.Posted
            } catch (e: Exception) {
                Log.e("PostViewModel", "onPostButtonClicked: ", e)
                _postState.value = PostState.ErrorPosting
            }
        }
    }
}