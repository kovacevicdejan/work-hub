package com.example.workhub.data.retrofit.models

data class Post(
    val _id: String,
    val post_type: String,
    val creator_type: Int,
    val creator: String,
    val post_text: String,
    val post_image: String,
    val job_title: String,
    val page_name: String,
    val date_posted: Long,
    var comments: List<Comment>
)

data class Comment(
    val user: String,
    val profile_image: String,
    val text: String
)
