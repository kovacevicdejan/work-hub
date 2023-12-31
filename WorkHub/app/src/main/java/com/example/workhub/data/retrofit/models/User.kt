package com.example.workhub.data.retrofit.models

data class User(
    val email: String,
    val password: String,
    val firstname: String,
    val lastname: String,
    val profile_image: String,
    val about: String,
    val headline: String,
    val location: String,
    val phone_number: String,
    val interests: String,
    val registration_date: Long,
    val connections: List<Connection>,
    val followed_pages: List<FollowedPage>,
    val saved_jobs: List<SavedJob>,
    val sent_invitations: List<Invitation>,
    var received_invitations: List<Invitation>,
    val experience: List<Experience>,
    var skills: List<Skill>,
)

data class Connection(
    val user: String
)

data class FollowedPage(
    val name: String
)

data class SavedJob(
    val id: String
)

data class Invitation(
    val user: String
)

data class Experience(
    val _id: String,
    val company: String,
    val job_title: String,
    val job_type: String,
    val start_date: String,
    val end_date: String,
    val location: String
)

data class Skill(
    val name: String
)
