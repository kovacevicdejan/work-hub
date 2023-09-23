package com.example.workhub.ui.stateholders

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.workhub.data.localdb.LocalChat
import com.example.workhub.data.repository.LocalChatRepository
import com.example.workhub.data.repository.UserRepository
import com.example.workhub.data.retrofit.models.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ChatsUiState(
    val user: User?,
    val chats: List<LocalChat>,
    val users: List<User>
)

@HiltViewModel
class ChatsViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val localChatRepository: LocalChatRepository
) : BaseViewModel<ChatEvent>() {
    private val _uiState = MutableStateFlow(
        ChatsUiState(
            user = null,
            chats = emptyList(),
            users = emptyList()
        )
    )

    val uiState = _uiState.asStateFlow()

    fun setUser(email: String) = viewModelScope.launch {
        val user = userRepository.getUserByEmail(email = email)
        _uiState.update { it.copy(user = user) }
    }

    fun getChats(user: String): Flow<List<LocalChat>> {
        Log.d("print", user)
        return localChatRepository.getChatsForUser(user = user)
    }

    fun getUsers(email: String, chats: List<LocalChat>) = viewModelScope.launch {
        var users: List<User> = emptyList()

        for(chat in chats) {
            val chatUser = userRepository.getUserByEmail(
                email = if(chat.user1 == email) chat.user2 else chat.user1
            )

            users = users.plus(chatUser)
        }

        _uiState.update { it.copy(users = users) }
    }
}