package com.example.workhub.data.retrofit.models

data class Page(
    val name: String,
    val headline: String,
    val industry: String,
    val location: String,
    val profile_image: String,
    val about: String,
    val website: String,
    val date_created: Long,
    val admin: String,
    val followers: List<Follower>,
    val reviews: List<PageReview>
)

data class Follower(
    val user: String
)

data class PageReview(
    val user: String,
    val text: String?
)
