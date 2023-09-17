package com.example.workhub.ui.stateholders

import androidx.lifecycle.viewModelScope
import com.example.workhub.data.WorkHubRepository
import com.example.workhub.data.retrofit.models.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class InvitationsUiState(
    val sent_invitations: List<User>,
    val received_invitation: List<User>
)

@HiltViewModel
class InvitationsViewModel @Inject constructor(
    private val workHubRepository: WorkHubRepository,
) : BaseViewModel<Event>() {
    private val _uiState = MutableStateFlow(
        InvitationsUiState(
            sent_invitations = emptyList(),
            received_invitation = emptyList()
        )
    )

    val uiState = _uiState.asStateFlow()

    fun getInvitations(curr_user: User) = viewModelScope.launch {
        var sentInvitations: List<User> = emptyList()
        var receivedInvitation: List<User> = emptyList()

        for(invitation in curr_user.sent_invitations) {
            val user = workHubRepository.getUserByEmail(invitation.user)
            sentInvitations = sentInvitations.plus(user)
        }

        curr_user.received_invitations.forEach {
            val user = workHubRepository.getUserByEmail(it.user)
            receivedInvitation = receivedInvitation.plus(user)
        }

        _uiState.update { it.copy(sent_invitations = sentInvitations) }
        _uiState.update { it.copy(received_invitation = receivedInvitation) }
    }
}