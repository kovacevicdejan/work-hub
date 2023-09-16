package com.example.workhub.data.retrofit.models

data class Chat(
    val user1: String?,
    val user2: String?,
    val messages: List<ChatMessage>?
)

data class ChatMessage(
    val user: String?,
    val text: String?,
    val date_sent: Long?
)
