package com.example.workhub.data.repository

import com.example.workhub.data.localdb.LocalChat
import com.example.workhub.data.localdb.LocalMessage
import com.example.workhub.data.localdb.WorkHubDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalMessageRepository @Inject constructor(private val workHubDao: WorkHubDao) {
    fun getMessages(id: String): Flow<List<LocalMessage>> {
        return workHubDao.getMessages(id = id)
    }

    suspend fun insert(localMessage: LocalMessage) {
        workHubDao.insertMessage(localMessage = localMessage)
    }

    suspend fun getLatestMessageTimestamp(): Long {
        return workHubDao.getLatestMessageTimestamp()
    }

    suspend fun getMessageCount(): Int {
        return workHubDao.getMessageCount()
    }

    suspend fun getUnreadMessagesCount(chat_id: String): Int {
        return workHubDao.getUnreadMessagesCountForChat(chat_id = chat_id)
    }

    suspend fun readMessages(chat_id: String): Unit {
        workHubDao.readMessages(chat_id = chat_id)
    }
}