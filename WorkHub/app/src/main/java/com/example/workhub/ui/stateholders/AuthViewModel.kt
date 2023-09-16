package com.example.workhub.ui.stateholders

import androidx.lifecycle.viewModelScope
import com.example.workhub.data.WorkHubRepository
import com.example.workhub.data.retrofit.models.Credentials
import com.example.workhub.data.retrofit.models.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AuthUiState(
    val email: String,
    val password: String,
    val firstname: String,
    val lastname: String,
    // val profile_image: String,
    val about: String,
    val headline: String,
    val location: String,
    val phone_number: String,
    val industry: String,
    val valid_user: Boolean
)

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val workHubRepository: WorkHubRepository,
) : BaseViewModel<Event>() {
    private val _uiState = MutableStateFlow(
        AuthUiState(
            email = "",
            password = "",
            firstname = "",
            lastname = "",
            about = "",
            headline = "",
            location = "",
            phone_number = "",
            industry = "",
            valid_user = false
        )
    )

    val uiState = _uiState.asStateFlow()

    fun setEmail(email: String) {
        _uiState.update { it.copy(email = email) }
    }

    fun setPassword(password: String) {
        _uiState.update { it.copy(password = password) }
    }

    fun setFirstname(firstname: String) {
        _uiState.update { it.copy(firstname = firstname) }
    }

    fun setLastname(lastname: String) {
        _uiState.update { it.copy(lastname = lastname) }
    }

    fun setAbout(about: String) {
        _uiState.update { it.copy(about = about) }
    }

    fun setHeadline(headline: String) {
        _uiState.update { it.copy(headline = headline) }
    }

    fun setLocation(location: String) {
        _uiState.update { it.copy(location = location) }
    }

    fun setPhoneNumber(phone_number: String) {
        _uiState.update { it.copy(phone_number = phone_number) }
    }

    fun setIndustry(industry: String) {
        _uiState.update { it.copy(industry = industry) }
    }

    fun register() = viewModelScope.launch {
        val user = User(
            email = uiState.value.email,
            password = uiState.value.password,
            firstname = uiState.value.firstname,
            lastname = uiState.value.lastname,
            profile_image = "image",
            about = uiState.value.about,
            headline = uiState.value.headline,
            location = uiState.value.location,
            phone_number = uiState.value.phone_number,
            industry = uiState.value.industry,
            registration_date = 0,
            connections = emptyList(),
            followed_pages = emptyList(),
            saved_jobs = emptyList(),
            sent_invitations = emptyList(),
            received_invitations = emptyList(),
            experience = emptyList(),
            education = emptyList(),
            skills = emptyList()
        )

        workHubRepository.register(user = user)
        workHubRepository.setLoggedUser(uiState.value.email)
        sendEvent(LoginEvent.LoginSuccess)
    }

    fun login() = viewModelScope.launch {
        val credentials = Credentials(
            email = uiState.value.email,
            password = uiState.value.password
        )

        val message = workHubRepository.login(credentials = credentials)

        if(message == "Login successful!") {
            workHubRepository.setLoggedUser(uiState.value.email)
            sendEvent(LoginEvent.LoginSuccess)
        }
        else
            sendEvent(LoginEvent.LoginFailure)
    }
}