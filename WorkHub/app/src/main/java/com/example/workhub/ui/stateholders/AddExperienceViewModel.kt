package com.example.workhub.ui.stateholders

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.example.workhub.data.repository.ImageRepository
import com.example.workhub.data.repository.UserRepository
import com.example.workhub.data.retrofit.requests.AddExperienceRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AddExperienceUiState(
    val company: String,
    val job_title: String,
    val job_type: String,
    val start_date: String,
    val end_date: String,
    val location: String
)

@HiltViewModel
class AddExperienceViewModel @Inject constructor(
    private val userRepository: UserRepository
) : BaseViewModel<EditProfileEvent>() {
    private val _uiState = MutableStateFlow(
        AddExperienceUiState(
            company = "",
            job_title = "",
            job_type = "",
            start_date = "",
            end_date = "",
            location = ""
        )
    )

    val uiState = _uiState.asStateFlow()

    fun setCompany(company: String) {
        _uiState.update { it.copy(company = company) }
    }

    fun setJobTitle(job_title: String) {
        _uiState.update { it.copy(job_title = job_title) }
    }

    fun setJobType(job_type: String) {
        _uiState.update { it.copy(job_type = job_type) }
    }

    fun setStartDate(start_date: String) {
        _uiState.update { it.copy(start_date = start_date) }
    }

    fun setEndDate(end_date: String) {
        _uiState.update { it.copy(end_date = end_date) }
    }

    fun setLocation(location: String) {
        _uiState.update { it.copy(location = location) }
    }

    fun addExperience(email: String) = viewModelScope.launch {
        val addExperienceRequest = AddExperienceRequest(
            email = email,
            company = uiState.value.company,
            job_title = uiState.value.job_title,
            job_type = uiState.value.job_type,
            start_date = uiState.value.start_date,
            end_date = uiState.value.end_date,
            location = uiState.value.location
        )

        userRepository.addExperience(addExperienceRequest = addExperienceRequest)
        sendEvent(EditProfileEvent.AddExperienceEvent)
    }
}