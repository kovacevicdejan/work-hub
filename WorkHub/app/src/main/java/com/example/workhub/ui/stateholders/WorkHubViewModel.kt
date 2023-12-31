package com.example.workhub.ui.stateholders

import android.content.Context
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.workhub.data.localdb.LocalChat
import com.example.workhub.data.localdb.LocalMessage
import com.example.workhub.data.repository.ChatRepository
import com.example.workhub.data.repository.LocalChatRepository
import com.example.workhub.data.repository.LocalMessageRepository
import com.example.workhub.data.repository.UserRepository
import com.example.workhub.data.retrofit.models.User
import com.example.workhub.utils.SocketManager
import com.example.workhub.utils.makeStatusNotification
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

data class UiState(
    val curr_user: User?,
    val user: String,
    val skill: String,
    val page: String,
    val post_creator: String,
    val creator_type: Int,
    val job_id: String,
    val chat_id: String,
    val keyword: String,
    val post_id: String,
    val page_image: String
)

@HiltViewModel
class WorkHubViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val localChatRepository: LocalChatRepository,
    private val localMessageRepository: LocalMessageRepository,
    private val chatRepository: ChatRepository,
    @ApplicationContext private val context: Context
) : BaseViewModel<Event>() {
    init {
        SocketManager.getSocket()?.on("chat created") { args ->
            viewModelScope.launch {
                Log.d("print", uiState.value.curr_user?.email ?: "")

                if((uiState.value.curr_user?.email ?: "") == args[1] ||
                    (uiState.value.curr_user?.email ?: "") == args[2]) {

                    val localChat = LocalChat(
                        id = args[0].toString(),
                        user1 = args[1].toString(),
                        user2 = args[2].toString(),
                        timestamp = args[3] as Long
                    )

                    localChatRepository.insert(localChat = localChat)
                }
            }
        }

        SocketManager.getSocket()?.on("receive message") { args ->
            run {
                if(args[1].toString() == (uiState.value.curr_user?.email ?: "")) {
                    viewModelScope.launch {
                        val localMessage = LocalMessage(
                            user = args[0].toString(),
                            text = args[2].toString(),
                            timestamp = args[3] as Long,
                            chat = args[4].toString(),
                            read = 1
                        )

                        localMessageRepository.insert(localMessage = localMessage)
                        makeStatusNotification(context = context, "New message")
                    }
                }
            }
        }
    }

    private val _uiState = MutableStateFlow(
        UiState(
            curr_user = null,
            user = "",
            skill = "",
            page = "Microsoft",
            post_creator = "",
            creator_type = 0,
            job_id = "",
            chat_id = "",
            keyword = "",
            post_id = "",
            page_image = ""
        )
    )

    val uiState = _uiState.asStateFlow()

    fun setCurrUser(email: String) = viewModelScope.launch {
        val user = userRepository.getUserByEmail(email)
        _uiState.update { it.copy(curr_user = user) }
        sendEvent(SignInEvent.SignInSuccess)
    }

    fun getLoggedUser() = viewModelScope.launch {
        val email = userRepository.getLoggedUser()
        SocketManager.getSocket()?.emit("user", email)

        if(email != null) {
            val user = userRepository.getUserByEmail(email)
            _uiState.update { it.copy(curr_user = user) }
            sendEvent(GetUserEvent.GetUserSuccess)
            var timestamp: Long = 0

            if(localMessageRepository.getMessageCount() != 0)
                timestamp = localMessageRepository.getLatestMessageTimestamp()

            val modifiedChats = chatRepository.getModifiedChats(user = user.email, timestamp = timestamp)

            if(modifiedChats.isNotEmpty())
                makeStatusNotification(context = context, message = "New messages!")

            for(chat in modifiedChats) {
                if(localChatRepository.getChatExists(chat._id) == 0) {
                    val localChat = LocalChat(
                        id = chat._id,
                        user1 = chat.user1,
                        user2 = chat.user2,
                        timestamp = chat.timestamp
                    )

                    localChatRepository.insert(localChat = localChat)
                }

                for(message in chat.messages) {
                    val localMessage = LocalMessage(
                        user = message.user,
                        text = message.text,
                        timestamp = message.timestamp,
                        read = 0,
                        chat = chat._id
                    )

                    localMessageRepository.insert(localMessage = localMessage)
                }
            }
        }
        else {
            withContext(Dispatchers.IO) {
                Thread.sleep(500)
            }

            sendEvent(GetUserEvent.GetUserFailure)
        }
    }

    fun signOut() {
        _uiState.update { it.copy(curr_user = null) }
        userRepository.removeLoggedUser()
    }

    fun setUser(user: String) {
        _uiState.update { it.copy(user = user) }
    }

    fun setPage(page: String) {
        _uiState.update { it.copy(page = page) }
    }

    fun setSkill(skill: String) {
        _uiState.update { it.copy(skill = skill) }
    }

    fun setPostCreator(post_creator: String) {
        _uiState.update { it.copy(post_creator = post_creator) }
    }

    fun setCreatorType(creator_type: Int) {
        _uiState.update { it.copy(creator_type = creator_type) }
    }

    fun setJobId(job_id: String) {
        _uiState.update { it.copy(job_id = job_id) }
    }

    fun setPostId(post_id: String) {
        _uiState.update { it.copy(post_id = post_id) }
    }

    fun setChatId(chat_id: String) {
        _uiState.update { it.copy(chat_id = chat_id) }
    }

    fun setKeyword(keyword: String) {
        _uiState.update { it.copy(keyword = keyword) }
    }

    fun setPageImage(page_image: String) {
        _uiState.update { it.copy(page_image = page_image) }
    }

    fun addSkill() = viewModelScope.launch {
        userRepository.addSKill(
            user = uiState.value.curr_user?.email ?: "",
            skill = uiState.value.skill
        )

        val user = uiState.value.curr_user?.let { userRepository.getUserByEmail(it.email) }
        _uiState.update { it.copy(curr_user = user) }
    }
}