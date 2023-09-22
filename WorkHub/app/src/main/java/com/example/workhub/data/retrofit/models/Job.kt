package com.example.workhub.data.retrofit.models

data class Job(
    val _id: String,
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
    val area: String,
    val date_posted: Long,
    var applicants: List<Applicant>
)

data class Applicant(
    val user: String
)
