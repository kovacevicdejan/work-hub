package com.example.workhub.data.repository

import com.example.workhub.data.retrofit.WorkHubApi
import com.example.workhub.data.retrofit.models.*
import com.example.workhub.data.retrofit.requests.CreatePageRequest
import com.example.workhub.data.retrofit.requests.FollowRequest
import com.example.workhub.data.retrofit.requests.NewPostRequest
import com.example.workhub.data.retrofit.requests.SendPageReview
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

    suspend fun sendPageReview(user: String, page: String, text: String, user_image: String) {
        val sendPageReview = SendPageReview(
            user = user,
            page = page,
            text = text,
            user_image = user_image
        )

        workHubApi.sendPageReview(sendPageReview = sendPageReview)
    }

    suspend fun searchPages(keyword: String): List<Page> {
        return workHubApi.searchPages(keyword = keyword)
    }
}
