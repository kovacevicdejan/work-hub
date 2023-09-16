package com.example.workhub.data.retrofit

import com.example.workhub.data.retrofit.models.Credentials
import com.example.workhub.data.retrofit.models.User
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

const val BASE_URL = "https://1d98-2a06-5b00-800-8400-805-1fe2-68d3-a9bc.ngrok-free.app/"

interface WorkHubApi {
    @POST("user/register")
    suspend fun register(@Body user: User)

    @POST("user/login")
    suspend fun login(@Body credentials: Credentials): String

    @GET("user/get_user_by_email/{email}")
    suspend fun getUserByEmail(@Path("email") email: String): User
}
