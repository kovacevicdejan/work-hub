package com.example.workhub.ui.stateholders

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.example.workhub.data.repository.ImageRepository
import com.example.workhub.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class EditProfileUiState(
    val firstname: String,
    val lastname: String,
    val about: String,
    val headline: String,
    val location: String,
    val interests: String,
    val phone_number: String,
)

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val userRepository: UserRepository
) : BaseViewModel<Event>() {
    private val _uiState = MutableStateFlow(
        EditProfileUiState(
            firstname = "",
            lastname = "",
            about = "",
            headline = "",
            location = "",
            interests = "",
            phone_number = ""
        )
    )

    val uiState = _uiState.asStateFlow()

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

    fun setInterests(interests: String) {
        _uiState.update { it.copy(interests = interests) }
    }

    fun setPhoneNumber(phone_number: String) {
        _uiState.update { it.copy(phone_number = phone_number) }
    }

    fun editProfile(email: String) = viewModelScope.launch {
        userRepository.editProfile(
            email = email,
            firstname = uiState.value.firstname,
            lastname = uiState.value.lastname,
            about = uiState.value.about,
            headline = uiState.value.headline,
            location = uiState.value.location,
            interests = uiState.value.interests,
            phone_number = uiState.value.phone_number
        )

        sendEvent(EditProfileEvent.EditProfileSuccess)
    }
}