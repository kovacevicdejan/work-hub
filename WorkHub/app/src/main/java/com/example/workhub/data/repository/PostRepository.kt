package com.example.workhub.data.repository

import com.example.workhub.data.retrofit.WorkHubApi
import com.example.workhub.data.retrofit.models.Post
import com.example.workhub.data.retrofit.requests.AddCommentRequest
import com.example.workhub.data.retrofit.requests.NewPostRequest
import javax.inject.Inject

class PostRepository @Inject constructor(
    private val workHubApi: WorkHubApi
) {
    suspend fun newPost(
        post_type: String,
        creator_type: Int,
        creator: String,
        post_text: String,
        post_image: String,
        job_title: String,
        page_name: String
    ) {
        val newPostRequest = NewPostRequest(
            post_type = post_type,
            creator_type = creator_type,
            creator = creator,
            post_text = post_text,
            post_image = post_image,
            job_title = job_title,
            page_name = page_name
        )

        workHubApi.newPost(newPostRequest)
    }

    suspend fun getUserPosts(user: String): List<Post> {
        return workHubApi.getUserPosts(user = user)
    }

    suspend fun getPagePosts(page: String): List<Post> {
        return workHubApi.getPagePosts(page = page)
    }

    suspend fun getPosts(email: String): List<Post> {
        return workHubApi.getPosts(email = email)
    }

    suspend fun getPostById(post_id: String): Post {
        return workHubApi.getPostById(post_id = post_id)
    }

    suspend fun addComment(addCommentRequest: AddCommentRequest) {
        workHubApi.addComment(addCommentRequest = addCommentRequest)
    }
}
