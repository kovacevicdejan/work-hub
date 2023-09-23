package com.example.workhub.ui.stateholders

import androidx.lifecycle.viewModelScope
import com.example.workhub.data.repository.*
import com.example.workhub.data.retrofit.models.Job
import com.example.workhub.data.retrofit.models.Page
import com.example.workhub.data.retrofit.models.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SearchUiState(
    val users: List<User>,
    val pages: List<Page>,
    val jobs: List<Job>
)

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val pageRepository: PageRepository,
    private val jobRepository: JobRepository
) : BaseViewModel<ChatEvent>() {
    private val _uiState = MutableStateFlow(
        SearchUiState(
            users = emptyList(),
            pages = emptyList(),
            jobs = emptyList()
        )
    )

    val uiState = _uiState.asStateFlow()

    fun searchUsers(keyword: String) = viewModelScope.launch {
        val users = userRepository.searchUsers(keyword = keyword)
        _uiState.update { it.copy(users = users) }
    }

    fun searchPages(keyword: String) = viewModelScope.launch {
        val pages = pageRepository.searchPages(keyword = keyword)
        _uiState.update { it.copy(pages = pages) }
    }

    fun searchJobs(keyword: String) = viewModelScope.launch {
        val jobs = jobRepository.searchJobs(keyword = keyword)
        _uiState.update { it.copy(jobs = jobs) }
    }
}