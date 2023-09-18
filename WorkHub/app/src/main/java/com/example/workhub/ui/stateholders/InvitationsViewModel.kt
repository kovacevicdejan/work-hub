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

data class InvitationsUiState(
    val sent_invitations: List<User>, val received_invitation: List<User>
)

@HiltViewModel
class InvitationsViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : BaseViewModel<InvitationEvent>() {
    private val _uiState = MutableStateFlow(
        InvitationsUiState(
            sent_invitations = emptyList(), received_invitation = emptyList()
        )
    )

    val uiState = _uiState.asStateFlow()

    fun getInvitations(curr_user: User) = viewModelScope.launch {
        var sentInvitations: List<User> = emptyList()
        var receivedInvitation: List<User> = emptyList()

        for (invitation in curr_user.sent_invitations) {
            val user = userRepository.getUserByEmail(invitation.user)
            sentInvitations = sentInvitations.plus(user)
        }

        curr_user.received_invitations.forEach {
            val user = userRepository.getUserByEmail(it.user)
            receivedInvitation = receivedInvitation.plus(user)
        }

        _uiState.update { it.copy(sent_invitations = sentInvitations) }
        _uiState.update { it.copy(received_invitation = receivedInvitation) }
    }

    fun acceptInvitation(user1: String, user2: String) = viewModelScope.launch {
        userRepository.acceptInvitation(user1 = user1, user2 = user2)

        _uiState.update {
            it.copy(
                received_invitation = uiState.value.received_invitation.filter { user ->
                    user.email != user2
                }
            )
        }

        sendEvent(InvitationEvent.AcceptInvitationEvent)
    }

    fun declineInvitation(user1: String, user2: String) = viewModelScope.launch {
        userRepository.declineInvitation(user1 = user1, user2 = user2)

        _uiState.update {
            it.copy(
                received_invitation = uiState.value.received_invitation.filter { user ->
                    user.email != user2
                }
            )
        }

        sendEvent(InvitationEvent.DeclineInvitationEvent)
    }

    fun withdrawInvitation(user1: String, user2: String) = viewModelScope.launch {
        userRepository.withdrawInvitation(user1 = user1, user2 = user2)

        _uiState.update {
            it.copy(
                sent_invitations = uiState.value.sent_invitations.filter { user ->
                    user.email != user2
                }
            )
        }

        sendEvent(InvitationEvent.WithdrawInvitationEvent)
    }
}