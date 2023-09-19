package com.example.workhub.data.retrofit.models

data class Job(
    val _id: String,
    val title: String,
    val page: String,
    val workplace_type: String,
    val location: String,
    val level: String,
    val description: String,
    val deadline: Long,
    val date_posted: Long,
    val applicants: List<Applicant>
)

data class Applicant(
    val user: String
)
