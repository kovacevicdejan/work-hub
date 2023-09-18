package com.example.workhub.ui.stateholders

import androidx.lifecycle.viewModelScope
import com.example.workhub.data.repository.UserRepository
import com.example.workhub.data.retrofit.models.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ManageNetworkUiState(
    val connections: List<User>
)

@HiltViewModel
class ManageNetworkViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : BaseViewModel<ConnectEvent>() {
    private val _uiState = MutableStateFlow(
        ManageNetworkUiState(
            connections = emptyList()
        )
    )

    val uiState = _uiState.asStateFlow()

    fun getConnections(curr_user: User) = viewModelScope.launch {
        var connections: List<User> = emptyList()

        for (invitation in curr_user.connections) {
            val user = userRepository.getUserByEmail(invitation.user)
            connections = connections.plus(user)
        }

        _uiState.update { it.copy(connections = connections) }
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
}