package com.example.workhub.ui.stateholders

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.workhub.data.repository.JobRepository
import com.example.workhub.data.repository.PageRepository
import com.example.workhub.data.repository.UserRepository
import com.example.workhub.data.retrofit.models.Applicant
import com.example.workhub.data.retrofit.models.Job
import com.example.workhub.data.retrofit.models.Page
import com.example.workhub.data.retrofit.models.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

data class JobUiState(
    val job: Job?,
    val page: Page?,
    val applicants: List<User>
)

@HiltViewModel
class JobViewModel @Inject constructor(
    private val jobRepository: JobRepository,
    private val pageRepository: PageRepository,
    private val userRepository: UserRepository
) : BaseViewModel<JobEvent>() {
    private val _uiState = MutableStateFlow(
        JobUiState(
            job = null,
            page = null,
            applicants = emptyList()
        )
    )

    val uiState = _uiState.asStateFlow()

    fun getJob(job_id: String) = viewModelScope.launch {
        val job = jobRepository.getJobById(job_id = job_id)
        _uiState.update { it.copy(job = job) }

        val page = pageRepository.getPageByName(name = job.page)
        _uiState.update { it.copy(page = page) }
        var applicants: List<User> = emptyList()

        for(applicant in job.applicants) {
            val user = userRepository.getUserByEmail(email = applicant.user)
            applicants = applicants.plus(user)
        }

        _uiState.update { it.copy(applicants = applicants) }
    }

    fun getDay(): Int {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = uiState.value.job?.deadline ?: 0
        return calendar.get(Calendar.DAY_OF_MONTH)
    }

    fun getMonth(): Int {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = uiState.value.job?.deadline ?: 0
        return calendar.get(Calendar.MONTH) + 1
    }

    fun getYear(): Int {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = uiState.value.job?.deadline ?: 0
        return calendar.get(Calendar.YEAR)
    }

    fun applyForJob(user: String, job_id: String) = viewModelScope.launch {
        jobRepository.applyForJob(
            user = user,
            job_id = job_id
        )

        sendEvent(JobEvent.ApplyForJob)
    }
}