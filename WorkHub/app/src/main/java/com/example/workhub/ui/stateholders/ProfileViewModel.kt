package com.example.workhub.ui.stateholders

import androidx.lifecycle.ViewModel
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

data class ProfileUiState(
    val user: User?,
)

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRepository: UserRepository
) : BaseViewModel<ConnectEvent>() {
    private val _uiState = MutableStateFlow(
        ProfileUiState(
            user = null
        )
    )

    val uiState = _uiState.asStateFlow()

    fun getUser(email: String) = viewModelScope.launch {
        val user = userRepository.getUserByEmail(email = email)
        _uiState.update { it.copy(user = user) }
    }

    fun connect(user1: String, user2: String) = viewModelScope.launch {
        userRepository.connect(
            user1 = user1,
            user2 = user2
        )

        if(uiState.value.user != null) {
            uiState.value.user!!.received_invitations =
                uiState.value.user!!.received_invitations.plus(Invitation(user = user1))
            _uiState.update { it.copy(user = uiState.value.user) }
        }

        sendEvent(ConnectEvent.ConnectSuccess)
    }
}