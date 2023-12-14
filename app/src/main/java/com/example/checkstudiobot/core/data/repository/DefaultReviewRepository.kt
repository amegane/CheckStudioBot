package com.example.checkstudiobot.core.data.repository

import com.example.checkstudiobot.core.data.model.Review
import com.example.checkstudiobot.core.network.firebase.FirebaseApi
import javax.inject.Inject

class DefaultReviewRepository @Inject constructor(
    private val firebaseApi: FirebaseApi,
) : ReviewRepository {
    override suspend fun getReview(reviewId: String): Review? {
        return firebaseApi.getReview(reviewId)
    }

    override suspend fun getReviews(): List<Review> {
        return firebaseApi.getReviews()
    }

    override suspend fun postReview(review: Review) {
        firebaseApi.postReview(review)
    }
}
