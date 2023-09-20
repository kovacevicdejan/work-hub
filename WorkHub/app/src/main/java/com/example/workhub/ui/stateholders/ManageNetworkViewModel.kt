package com.example.workhub.ui.stateholders

import androidx.lifecycle.viewModelScope
import com.example.workhub.data.repository.PageRepository
import com.example.workhub.data.repository.UserRepository
import com.example.workhub.data.retrofit.models.Page
import com.example.workhub.data.retrofit.models.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ManageNetworkUiState(
    val connections: List<User>,
    val followed_pages: List<Page>,
    val user: User?
)

@HiltViewModel
class ManageNetworkViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val pageRepository: PageRepository
) : BaseViewModel<Event>() {
    private val _uiState = MutableStateFlow(
        ManageNetworkUiState(
            connections = emptyList(),
            followed_pages = emptyList(),
            user = null
        )
    )

    val uiState = _uiState.asStateFlow()

    fun getConnections(email: String) = viewModelScope.launch {
        val user = userRepository.getUserByEmail(email = email)
        _uiState.update { it.copy(user = user) }
        var connections: List<User> = emptyList()

        for (connection in user.connections) {
            val usr = userRepository.getUserByEmail(connection.user)
            connections = connections.plus(usr)
        }

        _uiState.update { it.copy(connections = connections) }
    }

    fun getFollowedPages(email: String) = viewModelScope.launch {
        val user = userRepository.getUserByEmail(email = email)
        _uiState.update { it.copy(user = user) }
        var pages: List<Page> = emptyList()

        for (followed_page in user.followed_pages) {
            val page = pageRepository.getPageByName(followed_page.name)
            pages = pages.plus(page)
        }

        _uiState.update { it.copy(followed_pages = pages) }
    }

    fun removeConnection(user1: String, user2: String) = viewModelScope.launch {
        userRepository.removeConnection(user1 = user1, user2 = user2)

        _uiState.update {
            it.copy(
                connections = uiState.value.connections.filter { user ->
                    user.email != user2
                }
            )
        }

        sendEvent(ConnectEvent.RemoveConnection)
    }

    fun unfollow(user: String, name: String) = viewModelScope.launch {
        pageRepository.unfollow(user = user, page = name)

        _uiState.update {
            it.copy(
                followed_pages = uiState.value.followed_pages.filter { page ->
                    page.name != name
                }
            )
        }

        sendEvent(PageEvent.UnfollowPageEvent)
    }
}