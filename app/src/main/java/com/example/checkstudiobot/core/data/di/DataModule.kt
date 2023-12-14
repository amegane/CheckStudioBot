package com.example.checkstudiobot.core.data.di

import com.example.checkstudiobot.core.data.repository.AuthRepository
import com.example.checkstudiobot.core.data.repository.DefaultAuthRepository
import com.example.checkstudiobot.core.data.repository.DefaultReviewRepository
import com.example.checkstudiobot.core.data.repository.ReviewRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
interface DataModule {
    @Binds
    fun bindReviewRepository(
        defaultReviewRepository: DefaultReviewRepository,
    ): ReviewRepository

    @Binds
    fun bindAuthRepository(
        defaultAuthRepository: DefaultAuthRepository,
    ): AuthRepository
}

