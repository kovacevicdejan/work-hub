package com.example.workhub.data.repository

import com.example.workhub.data.retrofit.WorkHubApi
import com.example.workhub.data.retrofit.models.Option
import com.example.workhub.data.retrofit.requests.NewPostRequest
import javax.inject.Inject

class PostRepository @Inject constructor(
    private val workHubApi: WorkHubApi
) {
    suspend fun newPost(
        visibility: Int,
        post_type: String,
        creator_type: Int,
        creator: String,
        post_text: String,
        post_image: String,
        job_title: String,
        page_name: String,
        options: List<Option>
    ) {
        val newPostRequest = NewPostRequest(
            visibility = visibility,
            post_type = post_type,
            creator_type = creator_type,
            creator = creator,
            post_text = post_text,
            post_image = post_image,
            job_title = job_title,
            page_name = page_name,
            options = options
        )

        workHubApi.newPost(newPostRequest)
    }
}
