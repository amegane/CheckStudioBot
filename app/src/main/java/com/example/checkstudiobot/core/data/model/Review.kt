package com.example.checkstudiobot.core.data.model

data class Review(
    val id: String,
    val shopName: String,
    val reviewTitle: String,
    val reviewBody: String,
    val reviewPoint: Float,
    val imageUrls: List<String>,
)
