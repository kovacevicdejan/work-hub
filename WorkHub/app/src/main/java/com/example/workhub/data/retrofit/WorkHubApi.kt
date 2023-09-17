package com.example.workhub.data.retrofit

import com.example.workhub.data.retrofit.models.User
import com.example.workhub.data.retrofit.requests.ConnectRequest
import com.example.workhub.data.retrofit.requests.RegistrationRequest
import com.example.workhub.data.retrofit.requests.SignInRequest
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import java.io.File

const val BASE_URL = "https://13c8-2a06-5b00-800-8400-73b5-96a9-ddb7-3fa.ngrok-free.app/"

interface WorkHubApi {
    @POST("user/register")
    suspend fun register(@Body registrationRequest: RegistrationRequest)

    @POST("user/sign_in")
    suspend fun signIn(@Body signInRequest: SignInRequest): String

    @GET("user/get_user_by_email/{email}")
    suspend fun getUserByEmail(@Path("email") email: String): User

    @Multipart
    @POST("image/upload")
    suspend fun uploadImage(@Part file: MultipartBody.Part)

    @GET("user/get_users_by_industry/{industry}")
    suspend fun getUsersByIndustry(@Path("industry") industry: String): List<User>

    @POST("user/connect")
    suspend fun connect(@Body connectRequest: ConnectRequest)
}
