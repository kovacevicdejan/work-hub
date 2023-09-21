package com.example.workhub.data.retrofit.models

data class Chat(
    val _id: String,
    val user1: String,
    val user2: String,
    val timestamp: Long,
    val messages: List<ChatMessage>
)

data class ChatMessage(
    val user: String,
    val text: String,
    val timestamp: Long
)
