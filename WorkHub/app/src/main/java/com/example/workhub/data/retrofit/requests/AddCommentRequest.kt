package com.example.workhub.data.retrofit.requests

data class AddCommentRequest(
    val user: String,
    val profile_image: String,
    val post_id: String,
    val text: String
)