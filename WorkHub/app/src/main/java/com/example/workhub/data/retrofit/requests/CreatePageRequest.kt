package com.example.workhub.data.retrofit.requests

import android.net.Uri

data class CreatePageRequest(
    val name: String,
    val headline: String,
    val industry: String,
    val location: String,
    val profile_image: String,
    val about: String,
    val website: String,
    val admin: String
)