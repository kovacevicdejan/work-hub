package com.example.workhub.data.retrofit

import com.example.workhub.data.retrofit.models.Page
import com.example.workhub.data.retrofit.models.Post
import com.example.workhub.data.retrofit.models.User
import com.example.workhub.data.retrofit.requests.*
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

const val BASE_URL = "https://c60e-178-237-216-201.ngrok-free.app/"

interface WorkHubApi {
    @Multipart
    @POST("image/upload")
    suspend fun uploadImage(@Part file: MultipartBody.Part)

    @POST("user/register")
    suspend fun register(@Body registrationRequest: RegistrationRequest)

    @POST("user/sign_in")
    suspend fun signIn(@Body signInRequest: SignInRequest): String

    @GET("user/get_user_by_email/{email}")
    suspend fun getUserByEmail(@Path("email") email: String): User

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

    @POST("user/edit_profile")
    suspend fun editProfile(@Body editProfileRequest: EditProfileRequest)

    @POST("user/add_skill")
    suspend fun addSkill(@Body addSkillRequest: AddSkillRequest)

    @POST("user/add_experience")
    suspend fun addExperience(@Body addExperienceRequest: AddExperienceRequest)

    @POST("post/new_post")
    suspend fun newPost(@Body newPostRequest: NewPostRequest)

    @GET("post/get_user_posts/{user}")
    suspend fun getUserPosts(@Path("user") user: String): List<Post>

    @POST("page/create_page")
    suspend fun createPage(@Body createPageRequest: CreatePageRequest)

    @GET("page/get_page_by_name/{name}")
    suspend fun getPageByName(@Path("name") name: String): Page
}
