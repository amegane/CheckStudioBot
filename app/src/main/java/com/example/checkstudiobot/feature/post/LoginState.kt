package com.example.checkstudiobot.feature.post

sealed interface LoginState {
    data object LoggedIn : LoginState

    data object LoggedOut : LoginState
}
