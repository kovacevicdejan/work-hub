package com.example.workhub.ui.stateholders

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.workhub.data.localdb.LocalChat
import com.example.workhub.data.repository.LocalChatRepository
import com.example.workhub.data.repository.UserRepository
import com.example.workhub.data.retrofit.models.User
import com.example.workhub.utils.SocketManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SelectNewChatUiState(
    val user: User?,
    val connections: List<User>,
    val chat_id: String
)

@HiltViewModel
class SelectNewChatViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val localChatRepository: LocalChatRepository
) : BaseViewModel<ChatEvent>() {
    init {
        SocketManager.getSocket()?.on("chat created") { args ->
            viewModelScope.launch {
                Log.d("print", uiState.value.user?.email ?: "")

                if((uiState.value.user?.email ?: "") == args[1] ||
                    (uiState.value.user?.email ?: "") == args[2]) {
                    _uiState.update { it.copy(chat_id = args[0].toString()) }

                    val localChat = LocalChat(
                        id = args[0].toString(),
                        user1 = args[1].toString(),
                        user2 = args[2].toString(),
                        timestamp = args[3] as Long
                    )

                    localChatRepository.insert(localChat = localChat)
                    sendEvent(ChatEvent.NewChatEvent)
                }
            }
        }
    }

    private val _uiState = MutableStateFlow(
        SelectNewChatUiState(
            user = null,
            connections = emptyList(),
            chat_id = ""
        )
    )

    val uiState = _uiState.asStateFlow()

    fun getUser(email: String) = viewModelScope.launch {
        val user = userRepository.getUserByEmail(email = email)
        _uiState.update { it.copy(user = user) }

        var connections = userRepository.getConnections(user = email)
        val users = localChatRepository.getChatUsersForUser(user = email)

        connections = connections.filter {
            !users.contains(it.email)
        }

        _uiState.update { it.copy(connections = connections) }
    }

    fun newChat(user1: String, user2: String) {
        SocketManager.getSocket()?.emit("new chat", user1, user2)
    }
}