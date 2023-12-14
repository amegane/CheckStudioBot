package com.example.checkstudiobot.feature.post

sealed interface PostState {
    data object Default : PostState

    data object Posting : PostState

    data object ErrorPosting : PostState

    data object Posted : PostState
}