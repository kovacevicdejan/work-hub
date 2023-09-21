package com.example.workhub.data.repository

import com.example.workhub.data.retrofit.WorkHubApi
import com.example.workhub.data.retrofit.requests.NewChatRequest
import javax.inject.Inject

class ChatRepository @Inject constructor(
    private val workHubApi: WorkHubApi
) {
    suspend fun newChat(user1: String, user2: String): String {
        val newChatRequest = NewChatRequest(
            user1 = user1,
            user2 = user2
        )

        return workHubApi.newChat(newChatRequest = newChatRequest)
    }
}
