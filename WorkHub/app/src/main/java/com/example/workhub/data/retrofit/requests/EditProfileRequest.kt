package com.example.workhub.data.retrofit.requests

data class EditProfileRequest(
    val email: String,
    val firstname: String,
    val lastname: String,
    val about: String,
    val headline: String,
    val location: String,
    val interests: String,
    val phone_number: String,
)