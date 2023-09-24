package com.example.workhub.data.retrofit

import com.example.workhub.data.retrofit.models.*
import com.example.workhub.data.retrofit.requests.*
import com.example.workhub.utils.PostUserPage
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

const val BASE_URL = "https://1c81-109-245-38-169.ngrok-free.app/"

interface WorkHubApi {
    // image routes

    @Multipart
    @POST("image/upload")
    suspend fun uploadImage(@Part file: MultipartBody.Part)

    // user routes

    @POST("user/register")
    suspend fun register(@Body registrationRequest: RegistrationRequest)

    @POST("user/sign_in")
    suspend fun signIn(@Body signInRequest: SignInRequest): String

    @GET("user/get_user_by_email/{email}")
    suspend fun getUserByEmail(@Path("email") email: String): User

    @GET("user/get_recommended_users/{email}")
    suspend fun getRecommendedUsers(@Path("email") email: String): List<User>

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

    @GET("user/get_connections/{user}")
    suspend fun getConnections(@Path("user") user: String): List<User>

    @GET("user/search/{keyword}")
    suspend fun searchUsers(@Path("keyword") keyword: String): List<User>

    // post routes

    @POST("post/new_post")
    suspend fun newPost(@Body newPostRequest: NewPostRequest)

    @GET("post/get_user_posts/{user}")
    suspend fun getUserPosts(@Path("user") user: String): List<Post>

    @GET("post/get_page_posts/{page}")
    suspend fun getPagePosts(@Path("page") page: String): List<Post>

    @GET("post/get_posts/{email}")
    suspend fun getPosts(
        @Path("email") email: String,
        @Query("timestamp") timestamp: Long,
        @Query("page") page: Int
    ): List<Post>

    @GET("post/get_post_by_id/{post_id}")
    suspend fun getPostById(@Path("post_id") post_id: String): Post

    @POST("post/add_comment")
    suspend fun addComment(@Body addCommentRequest: AddCommentRequest)

    // page routes

    @POST("page/create_page")
    suspend fun createPage(@Body createPageRequest: CreatePageRequest)

    @GET("page/get_page_by_name/{name}")
    suspend fun getPageByName(@Path("name") name: String): Page

    @GET("page/get_recommended_pages/{email}")
    suspend fun getRecommendedPages(@Path("email") email: String): List<Page>

    @POST("page/follow")
    suspend fun follow(@Body followRequest: FollowRequest)

    @POST("page/unfollow")
    suspend fun unfollow(@Body followRequest: FollowRequest)

    @POST("page/send_review")
    suspend fun sendPageReview(@Body sendPageReview: SendPageReview)

    @GET("page/search/{keyword}")
    suspend fun searchPages(@Path("keyword") keyword: String): List<Page>

    // job routes

    @POST("job/new_job")
    suspend fun newJob(@Body newJobRequest: NewJobRequest)

    @GET("job/get_page_jobs/{page}")
    suspend fun getPageJobs(@Path("page") page: String): List<Job>

    @POST("job/delete")
    suspend fun deleteJob(@Body deleteJobRequest: DeleteJobRequest)

    @GET("job/get_job_by_id/{job_id}")
    suspend fun getJobById(@Path("job_id") job_id: String): Job

    @POST("job/apply")
    suspend fun applyForJob(@Body applyForJobRequest: ApplyForJobRequest)

    @GET("job/search/{keyword}")
    suspend fun searchJobs(@Path("keyword") keyword: String): List<Job>

    @GET("job/get_jobs")
    suspend fun getJobs(): List<Job>

    // chat routes

    @GET("chat/get_modified_chats/{user}")
    suspend fun getModifiedChats(
        @Path("user") user: String,
        @Query("timestamp") timestamp: Long
    ): List<Chat>
}
