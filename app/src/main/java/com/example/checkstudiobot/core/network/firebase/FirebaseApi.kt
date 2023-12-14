package com.example.checkstudiobot.core.network.firebase

import com.example.checkstudiobot.core.data.model.Review
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

interface FirebaseApi {
    suspend fun getReview(reviewId: String): Review?

    suspend fun getReviews(): List<Review>

    suspend fun postReview(review: Review)
}

@Singleton
class DefaultFirebaseApi @Inject constructor(
    val db: FirebaseFirestore,
): FirebaseApi {
    override suspend fun getReview(reviewId: String): Review? {
        return db.collection(REVIEW_COLLECTION_PATH).document(reviewId).get().await().toObject(Review::class.java)
    }

    override suspend fun getReviews(): List<Review> {
        return db.collection(REVIEW_COLLECTION_PATH).get().await().toObjects(Review::class.java)
    }

    override suspend fun postReview(review: Review) {
        db.collection(REVIEW_COLLECTION_PATH).add(review).await()
    }

    companion object {
        private const val REVIEW_COLLECTION_PATH = "reviews"
    }
}
