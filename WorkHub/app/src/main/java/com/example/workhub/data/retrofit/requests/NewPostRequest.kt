package com.example.workhub.data.retrofit.requests

data class NewPostRequest(
    val post_type: String,
    val creator_type: Int,
    val creator: String,
    val post_text: String,
    val post_image: String,
    val job_title: String,
    val page_name: String
)