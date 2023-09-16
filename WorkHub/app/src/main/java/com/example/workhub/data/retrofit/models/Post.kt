package com.example.workhub.data.retrofit.models

data class Post(
    val name: String?,
    val headline: String?,
    val industry: String?,
    val location: String?,
    val profile_image: String?,
    val about: String?,
    val website: String?,
    val size: String?,
    val date_created: Long?,
    val admin: String?,
    val reviews: List<PostReview>?
)

data class PostReview(
    val user: String?,
    val text: String?
)
