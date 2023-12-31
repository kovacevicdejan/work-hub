package com.example.workhub.data.retrofit.requests

data class RegistrationRequest(
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
)