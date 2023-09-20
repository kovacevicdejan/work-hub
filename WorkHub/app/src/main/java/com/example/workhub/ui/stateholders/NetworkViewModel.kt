package com.example.workhub.ui.stateholders

import androidx.lifecycle.viewModelScope
import com.example.workhub.data.repository.PageRepository
import com.example.workhub.data.repository.UserRepository
import com.example.workhub.data.retrofit.models.Follower
import com.example.workhub.data.retrofit.models.Invitation
import com.example.workhub.data.retrofit.models.Page
import com.example.workhub.data.retrofit.models.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class NetworkUiState(
    val users: List<User>,
    val pages: List<Page>
)

@HiltViewModel
class NetworkViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val pageRepository: PageRepository
) : BaseViewModel<Event>() {
    private val _uiState = MutableStateFlow(
        NetworkUiState(
            users = emptyList(),
            pages = emptyList()
        )
    )

    val uiState = _uiState.asStateFlow()

    fun getRecommendedUsers(email: String) = viewModelScope.launch {
        val users = userRepository.getRecommendedUsers(email = email)
        _uiState.update { it.copy(users = users) }
    }

    fun getRecommendedPages(email: String) = viewModelScope.launch {
        val pages = pageRepository.getRecommendedPages(email = email)
        _uiState.update { it.copy(pages = pages) }
    }

    fun connect(user1: String, user2: String) = viewModelScope.launch {
        userRepository.connect(
            user1 = user1,
            user2 = user2
        )

        _uiState.update {
            it.copy(
                users = uiState.value.users.map { user ->
                    if (user.email == user2)
                        user.received_invitations =
                            user.received_invitations.plus(Invitation(user = user1))

                    user
                }
            )
        }

        sendEvent(ConnectEvent.ConnectSuccess)
    }

    fun follow(user: String, name: String) = viewModelScope.launch {
        pageRepository.follow(user = user, page = name)

        _uiState.update {
            it.copy(
                pages = uiState.value.pages.map { page ->
                    if (page.name == name)
                        page.followers =
                            page.followers.plus(Follower(user = user))

                    page
                }
            )
        }

        sendEvent(PageEvent.FollowPageEvent)
    }
}