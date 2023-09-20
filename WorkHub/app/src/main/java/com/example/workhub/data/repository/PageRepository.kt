package com.example.workhub.data.repository

import com.example.workhub.data.retrofit.WorkHubApi
import com.example.workhub.data.retrofit.models.FollowedPage
import com.example.workhub.data.retrofit.models.Option
import com.example.workhub.data.retrofit.models.Page
import com.example.workhub.data.retrofit.models.Post
import com.example.workhub.data.retrofit.requests.CreatePageRequest
import com.example.workhub.data.retrofit.requests.FollowRequest
import com.example.workhub.data.retrofit.requests.NewPostRequest
import javax.inject.Inject

class PageRepository @Inject constructor(
    private val workHubApi: WorkHubApi
) {
    suspend fun createPage(createPageRequest: CreatePageRequest) {
        workHubApi.createPage(createPageRequest = createPageRequest)
    }

    suspend fun getPageByName(name: String): Page {
        return workHubApi.getPageByName(name = name)
    }

    suspend fun getRecommendedPages(email: String): List<Page> {
        return workHubApi.getRecommendedPages(email = email)
    }

    suspend fun follow(user: String, page: String) {
        val followRequest = FollowRequest(
            user = user,
            page = page
        )

        workHubApi.follow(followRequest = followRequest)
    }

    suspend fun unfollow(user: String, page: String) {
        val followRequest = FollowRequest(
            user = user,
            page = page
        )

        workHubApi.unfollow(followRequest = followRequest)
    }
}
