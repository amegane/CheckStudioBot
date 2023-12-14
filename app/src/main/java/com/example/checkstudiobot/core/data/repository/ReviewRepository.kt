package com.example.checkstudiobot.core.data.repository

import com.example.checkstudiobot.core.data.model.Review

interface ReviewRepository {
    suspend fun getReview(reviewId: String): Review?

    suspend fun getReviews(): List<Review>

    suspend fun postReview(review: Review)
}
