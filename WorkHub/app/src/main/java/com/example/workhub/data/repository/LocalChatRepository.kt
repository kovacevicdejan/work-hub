package com.example.workhub.data.repository

import com.example.workhub.data.localdb.LocalChat
import com.example.workhub.data.localdb.WorkHubDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalChatRepository @Inject constructor(private val workHubDao: WorkHubDao) {
    suspend fun insert(localChat: LocalChat) {
        workHubDao.insertChat(localChat = localChat)
    }

    fun getChatsForUser(user: String): Flow<List<LocalChat>> {
        return workHubDao.getChatsForUser(user = user)
    }

    suspend fun getChatUsersForUser(user: String): List<String> {
        return workHubDao.getChatUsersForUser(user = user)
    }

    suspend fun getChatById(id: String): LocalChat {
        return workHubDao.getChatById(id = id)
    }
}