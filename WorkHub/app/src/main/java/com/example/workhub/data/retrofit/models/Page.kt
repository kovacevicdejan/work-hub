package com.example.workhub.data.retrofit.models

data class Page(
    val visibility: String,
    val post_type: String,
    val creator_type: String,
    val creator: String,
    val text: String,
    val image: String,
    val job_title: String,
    val page: String,
    val options: List<Option>,
    val date_posted: Long,
    val comments: List<Comment>,
    val likes: List<Like>
)

data class Option(
    val option: String
)

data class Comment(
    val user: String,
    val text: String,
    val replying_user: String,
    val replying_text: String
)

data class Like(
    val user: String
)
