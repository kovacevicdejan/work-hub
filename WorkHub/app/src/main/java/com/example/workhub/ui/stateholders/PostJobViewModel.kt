package com.example.workhub.ui.stateholders

import androidx.lifecycle.viewModelScope
import com.example.workhub.data.repository.JobRepository
import com.example.workhub.data.retrofit.requests.NewJobRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class PostJobUiState(
    val title: String,
    val workplace_type: String,
    val location: String,
    val job_type: Boolean,
    val level: String,
    val description: String,
    val tech_stack: String,
    val deadline: Long,
    val area: String
)

@HiltViewModel
class PostJobViewModel @Inject constructor(
    private val jobRepository: JobRepository,
) : BaseViewModel<JobEvent>() {
    private val _uiState = MutableStateFlow(
        PostJobUiState(
            title = "",
            workplace_type = "",
            location = "",
            job_type = true,
            level = "",
            description = "",
            tech_stack = "",
            deadline = 0,
            area = ""
        )
    )

    val uiState = _uiState.asStateFlow()

    fun setTitle(title: String) {
        _uiState.update { it.copy(title = title) }
    }

    fun setWorkplaceType(workplace_type: String) {
        _uiState.update { it.copy(workplace_type = workplace_type) }
    }

    fun setLocation(location: String) {
        _uiState.update { it.copy(location = location) }
    }

    fun setJobType(job_type: Boolean) {
        _uiState.update { it.copy(job_type = job_type) }
    }

    fun setLevel(level: String) {
        _uiState.update { it.copy(level = level) }
    }

    fun setDescription(description: String) {
        _uiState.update { it.copy(description = description) }
    }

    fun setTechStack(tech_stack: String) {
        _uiState.update { it.copy(tech_stack = tech_stack) }
    }

    fun setDeadline(deadline: Long) {
        _uiState.update { it.copy(deadline = deadline) }
    }

    fun setArea(area: String) {
        _uiState.update { it.copy(area = area) }
    }

    fun postJob(page: String, page_image: String) = viewModelScope.launch {
        val newJobRequest = NewJobRequest(
            title = uiState.value.title,
            page = page,
            page_image = page_image,
            workplace_type = uiState.value.workplace_type,
            location = uiState.value.location,
            job_type = if(uiState.value.job_type) 0 else 1,
            level = uiState.value.level,
            description = uiState.value.description,
            tech_stack = uiState.value.tech_stack,
            deadline = uiState.value.deadline,
            area = uiState.value.area
        )

        jobRepository.newJob(newJobRequest = newJobRequest)
        sendEvent(JobEvent.NewJobEvent)
    }
}