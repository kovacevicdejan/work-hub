package com.example.workhub.data.retrofit.models

data class Post(
    val _id: String,
    val visibility: Int,
    val post_type: String,
    val creator_type: Int,
    val creator: String,
    val post_text: String,
    val post_image: String,
    val job_title: String,
    val page_name: String,
    val options: List<Option>,
    val date_posted: Long,
    val comments: List<Comment>,
    val likes: List<Like>
)

data class Option(
    val text: String,
    val voters: List<Voter>
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

data class Voter(
    val user: String
)
