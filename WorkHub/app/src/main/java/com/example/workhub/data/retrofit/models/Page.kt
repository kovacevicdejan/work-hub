package com.example.workhub.data.retrofit.models

data class Page(
    val name: String,
    val headline: String,
    val location: String,
    val profile_image: String,
    val about: String,
    val website: String,
    val date_created: Long,
    val specialties: String,
    val admin: String,
    var followers: List<Follower>,
    var reviews: List<PageReview>
)

data class Follower(
    val user: String
)

data class PageReview(
    val user: String,
    val text: String,
    val user_image: String
)
