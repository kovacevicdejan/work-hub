package com.example.workhub.data.repository

import com.example.workhub.data.retrofit.WorkHubApi
import com.example.workhub.data.retrofit.models.Chat
import javax.inject.Inject

class ChatRepository @Inject constructor(
    private val workHubApi: WorkHubApi
) {
    suspend fun getModifiedChats(user: String, timestamp: Long): List<Chat> {
        return workHubApi.getModifiedChats(user = user, timestamp = timestamp)
    }
}
