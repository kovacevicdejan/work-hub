package com.example.workhub.ui.stateholders

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.workhub.data.repository.JobRepository
import com.example.workhub.data.repository.PageRepository
import com.example.workhub.data.retrofit.models.Applicant
import com.example.workhub.data.retrofit.models.Job
import com.example.workhub.data.retrofit.models.Page
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

data class JobUiState(
    val job: Job?,
    val page: Page?
)

@HiltViewModel
class JobViewModel @Inject constructor(
    private val jobRepository: JobRepository,
    private val pageRepository: PageRepository
) : BaseViewModel<JobEvent>() {
    private val _uiState = MutableStateFlow(
        JobUiState(
            job = null,
            page = null
        )
    )

    val uiState = _uiState.asStateFlow()

    fun getJob(job_id: String) = viewModelScope.launch {
        val job = jobRepository.getJobById(job_id = job_id)
        _uiState.update { it.copy(job = job) }

        val page = pageRepository.getPageByName(name = job.page)
        _uiState.update { it.copy(page = page) }

//        val jobs: List<Job> = jobRepository.getPageJobs(uiState.value.page?.name ?: "")
//        _uiState.update { it.copy(jobs = jobs) }
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