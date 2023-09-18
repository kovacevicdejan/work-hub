package com.example.workhub.ui.stateholders

import androidx.lifecycle.viewModelScope
import com.example.workhub.data.repository.UserRepository
import com.example.workhub.data.retrofit.models.Invitation
import com.example.workhub.data.retrofit.models.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class NetworkUiState(
    val users: List<User>
)

@HiltViewModel
class NetworkViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : BaseViewModel<ConnectEvent>() {
    private val _uiState = MutableStateFlow(
        NetworkUiState(
            users = emptyList()
        )
    )

    val uiState = _uiState.asStateFlow()

    fun getUsersByIndustry(user: String, industry: String) = viewModelScope.launch {
        var users = userRepository.getUsersByIndustry(industry = industry)
        users = users.filter { it.email != user }
        _uiState.update { it.copy(users = users) }
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
}