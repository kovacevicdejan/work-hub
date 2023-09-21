package com.example.workhub.data.localdb

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkHubDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertChat(localChat: LocalChat)

    @Query("select * from chat_table where user1 = :user or user2 = :user")
    fun getChatsForUser(user: String): Flow<List<LocalChat>>

    @Query("select user1 from chat_table where user2 = :user union " +
            "select user2 from chat_table where user1 = :user")
    suspend fun getChatUsersForUser(user: String): List<String>

    @Query("select * from chat_table where id = :id limit 1")
    suspend fun getChatById(id: String): LocalChat

    @Query("select * from message_table where chat = :id")
    fun getMessages(id: String): Flow<List<LocalMessage>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMessage(localMessage: LocalMessage)
}
