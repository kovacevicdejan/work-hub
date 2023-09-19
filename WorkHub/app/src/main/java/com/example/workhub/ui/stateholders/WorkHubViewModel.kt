package com.example.workhub.ui.stateholders

import androidx.lifecycle.viewModelScope
import com.example.workhub.data.repository.UserRepository
import com.example.workhub.data.retrofit.models.Skill
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
    val curr_user: User?,
    val user: String,
    val skill: String,
    val page: String
)

@HiltViewModel
class WorkHubViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : BaseViewModel<Event>() {
    private val _uiState = MutableStateFlow(
        UiState(
            curr_user = null,
            user = "",
            skill = "",
            page = "Microsoft"
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

        if(email != null) {
            val user = userRepository.getUserByEmail(email)
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

    fun addSkill() = viewModelScope.launch {
        userRepository.addSKill(
            user = uiState.value.curr_user?.email ?: "",
            skill = uiState.value.skill
        )

        val user = uiState.value.curr_user?.let { userRepository.getUserByEmail(it.email) }
        _uiState.update { it.copy(curr_user = user) }
    }
}