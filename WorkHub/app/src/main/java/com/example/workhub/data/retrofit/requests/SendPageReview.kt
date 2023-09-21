package com.example.workhub.data.retrofit.requests

data class SendPageReview (
    val user: String,
    val page: String,
    val text: String,
    val user_image: String
)