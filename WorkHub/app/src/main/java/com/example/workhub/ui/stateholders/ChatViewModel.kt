package com.example.workhub.ui.stateholders

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.workhub.data.localdb.LocalMessage
import com.example.workhub.data.repository.LocalChatRepository
import com.example.workhub.data.repository.LocalMessageRepository
import com.example.workhub.data.repository.UserRepository
import com.example.workhub.data.retrofit.models.ChatMessage
import com.example.workhub.data.retrofit.models.User
import com.example.workhub.ui.SocketManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.util.*
import javax.inject.Inject

data class ChatUiState(
    val user: User?,
    val other_user: User?,
    val text: String
)

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val localChatRepository: LocalChatRepository,
    private val localMessageRepository: LocalMessageRepository
) : BaseViewModel<ChatEvent>() {
    private val _uiState = MutableStateFlow(
        ChatUiState(
            user = null,
            other_user = null,
            text = ""
        )
    )

    val uiState = _uiState.asStateFlow()

    fun getUsers(email: String, chat_id: String) = viewModelScope.launch {
        val user = userRepository.getUserByEmail(email = email)
        _uiState.update { it.copy(user = user) }

        val chat = localChatRepository.getChatById(id = chat_id)
        val otherUser = userRepository.getUserByEmail(
            email = if(chat.user1 == email) chat.user2 else chat.user1
        )

        _uiState.update { it.copy(other_user = otherUser) }
    }

    fun getMessages(chat_id: String): Flow<List<LocalMessage>> {
        return localMessageRepository.getMessages(id = chat_id)
    }

    fun setText(text: String) {
        _uiState.update { it.copy(text = text) }
    }

    fun sendMessage(text: String, chat_id: String) = viewModelScope.launch {
        val timestamp = System.currentTimeMillis()

        val localMessage = LocalMessage(
            user = uiState.value.user?.email ?: "",
            text = text,
            timestamp = timestamp,
            chat = chat_id,
            read = 1
        )

        localMessageRepository.insert(localMessage = localMessage)
        SocketManager.getSocket()?.emit(
            "send message",
            uiState.value.user?.email ?: "",
            text,
            timestamp,
            chat_id
        )
    }

    fun getYear(timestamp: Long): Int {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = timestamp
        return calendar.get(Calendar.YEAR)
    }

    fun getMonth(timestamp: Long): String {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = timestamp
        return when(calendar.get(Calendar.MONTH) + 1) {
            1 -> "JAN"
            2 -> "FEB"
            3 -> "MAR"
            4 -> "APR"
            5 -> "MAY"
            6 -> "JUN"
            7 -> "JUL"
            8 -> "AUG"
            9 -> "SEP"
            10 -> "OCT"
            11 -> "NOV"
            12 -> "DEC"
            else -> "INVALID"
        }
    }

    fun getDay(timestamp: Long): Int {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = timestamp
        return calendar.get(Calendar.DAY_OF_MONTH)
    }

    fun divideMessages(messages: List<LocalMessage>): List<List<LocalMessage>> {
        var i = 0
        var res: List<List<LocalMessage>> = emptyList()

        while(i < messages.size) {
            val timestamp = messages[i].timestamp
            var list: List<LocalMessage> = emptyList()

            while(i < messages.size && messages[i].timestamp - timestamp < 86400000) {
                list = list.plus(messages[i])
                i++
            }

            res = res.plus(element = list)
        }

        return res
    }
}