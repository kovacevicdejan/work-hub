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
    val industry: String,
    val registration_date: Long,
    val connections: List<Connection>,
    val followed_pages: List<FollowedPage>,
    val saved_jobs: List<SavedJob>,
    val sent_invitations: List<Invitation>,
    val received_invitations: List<Invitation>,
    val experience: List<Experience>,
    val education: List<Education>,
    val skills: List<Skill>,
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
    val email: String
)

data class Experience(
    val company: String,
    val positions: List<JobPosition>
)

data class JobPosition(
    val job_title: String,
    val job_type: String,
    val start_date: Long,
    val end_date: Long,
    val location: String,
    val description: String
)

data class Education(
    val school: String,
    val positions: List<EducationPosition>
)

data class EducationPosition(
    val title: String,
    val start_year: Int,
    val end_year: Int,
    val location: String
)

data class Skill(
    val name: String
)

data class Credentials(
    val email: String,
    val password: String
)
