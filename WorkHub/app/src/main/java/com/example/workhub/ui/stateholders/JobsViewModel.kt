package com.example.workhub.ui.stateholders

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.workhub.data.repository.*
import com.example.workhub.data.retrofit.models.Job
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class JobsUiState(
    val jobs: List<Job>
)

@HiltViewModel
class JobsViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val pageRepository: PageRepository,
    private val jobRepository: JobRepository
) : BaseViewModel<ChatEvent>() {
    private val _uiState = MutableStateFlow(
        JobsUiState(
            jobs = emptyList()
        )
    )

    val uiState = _uiState.asStateFlow()

    fun getJobs() = viewModelScope.launch {
        val jobs = jobRepository.getJobs()
        _uiState.update { it.copy(jobs = jobs) }
    }

    fun sortJobs(sortFunction: (List<Job>) -> List<Job>) {
        Log.d("print", sortFunction(uiState.value.jobs).toString())
        _uiState.update { it.copy(jobs = sortFunction(uiState.value.jobs)) }
    }
}