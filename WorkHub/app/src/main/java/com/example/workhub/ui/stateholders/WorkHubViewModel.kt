package com.example.workhub.ui.stateholders

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.workhub.data.WorkHubRepository
import com.example.workhub.data.retrofit.models.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

data class UiState(
    val curr_user: User?
)

@HiltViewModel
class WorkHubViewModel @Inject constructor(
    private val workHubRepository: WorkHubRepository,
) : BaseViewModel<Event>() {
    private val _uiState = MutableStateFlow(
        UiState(
            curr_user = null
        )
    )

    val uiState = _uiState.asStateFlow()

    fun setCurrUser(email: String) = viewModelScope.launch {
        val user = workHubRepository.getUserByEmail(email)
        _uiState.update { it.copy(curr_user = user) }
        sendEvent(SignInEvent.SignInSuccess)
    }

    fun getLoggedUser() = viewModelScope.launch {
        val email = workHubRepository.getLoggedUser()

        if(email != null) {
            val user = workHubRepository.getUserByEmail(email)
            _uiState.update { it.copy(curr_user = user) }
            sendEvent(GetUserEvent.GetUserSuccess)
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
        workHubRepository.removeLoggedUser()
    }
}