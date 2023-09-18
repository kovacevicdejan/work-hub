package com.example.workhub.data.retrofit

import com.example.workhub.data.retrofit.models.User
import com.example.workhub.data.retrofit.requests.ConnectRequest
import com.example.workhub.data.retrofit.requests.NewPostRequest
import com.example.workhub.data.retrofit.requests.RegistrationRequest
import com.example.workhub.data.retrofit.requests.SignInRequest
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

const val BASE_URL = "https://396d-178-237-217-94.ngrok-free.app/"

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

    @POST("user/accept_invitation")
    suspend fun acceptInvitation(@Body connectRequest: ConnectRequest)

    @POST("user/decline_invitation")
    suspend fun declineInvitation(@Body connectRequest: ConnectRequest)

    @POST("user/withdraw_invitation")
    suspend fun withdrawInvitation(@Body connectRequest: ConnectRequest)

    @POST("user/remove_connection")
    suspend fun removeConnection(@Body connectRequest: ConnectRequest)

    @POST("post/new_post")
    suspend fun newPost(@Body newPostRequest: NewPostRequest)
}
