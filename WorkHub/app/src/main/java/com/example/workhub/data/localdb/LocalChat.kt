package com.example.workhub.data.localdb

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "chat_table")
data class LocalChat(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "user1") val user1: String,
    @ColumnInfo(name = "user2") val user2: String,
    @ColumnInfo(name = "timestamp") val timestamp: Long
)