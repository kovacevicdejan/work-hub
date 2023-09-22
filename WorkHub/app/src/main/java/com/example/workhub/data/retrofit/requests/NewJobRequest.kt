package com.example.workhub.data.retrofit.requests

data class NewJobRequest(
    val title: String,
    val page: String,
    val page_image: String,
    val workplace_type: String,
    val location: String,
    val job_type: Int,
    val level: String,
    val description: String,
    val tech_stack: String,
    val deadline: Long,
    val area: String
)