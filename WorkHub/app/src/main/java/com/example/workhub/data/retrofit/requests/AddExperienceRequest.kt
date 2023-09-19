package com.example.workhub.data.retrofit.requests

data class AddExperienceRequest(
    val email: String,
    val company: String,
    val job_title: String,
    val job_type: String,
    val start_date: String,
    val end_date: String,
    val location: String,
)