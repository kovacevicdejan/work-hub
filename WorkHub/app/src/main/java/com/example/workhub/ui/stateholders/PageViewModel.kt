package com.example.workhub.ui.stateholders

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workhub.data.repository.JobRepository
import com.example.workhub.data.repository.PageRepository
import com.example.workhub.data.repository.PostRepository
import com.example.workhub.data.retrofit.models.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class PageUiState(
    val page: Page?,
    val posts: List<Post>,
    val jobs: List<Job>,
    val text: String
)

@HiltViewModel
class PageViewModel @Inject constructor(
    private val pageRepository: PageRepository,
    private val postRepository: PostRepository,
    private val jobRepository: JobRepository
) : BaseViewModel<PageEvent>() {
    private val _uiState = MutableStateFlow(
        PageUiState(
            page = null,
            posts = emptyList(),
            jobs = emptyList(),
            text = ""
        )
    )

    val uiState = _uiState.asStateFlow()

    fun setText(text: String) {
        _uiState.update { it.copy(text = text) }
    }

    fun getPage(name: String, user: String) = viewModelScope.launch {
        Log.d("print", user)
        val page = pageRepository.getPageByName(name = name)
        _uiState.update { it.copy(page = page) }

        val posts: List<Post> = postRepository.getPagePosts(uiState.value.page?.name ?: "")
        _uiState.update { it.copy(posts = posts) }

        var jobs: List<Job> = jobRepository.getPageJobs(uiState.value.page?.name ?: "")
        jobs = jobs.filter {
            !it.applicants.contains(Applicant(user = user))
        }

        _uiState.update { it.copy(jobs = jobs) }
    }

    fun follow(user: String) = viewModelScope.launch {
        uiState.value.page?.let { pageRepository.follow(user = user, page = it.name) }

        if (uiState.value.page != null) {
            uiState.value.page!!.followers =
                uiState.value.page!!.followers.plus(Follower(user = user))
            _uiState.update { it.copy(page = uiState.value.page) }
        }

        sendEvent(PageEvent.FollowPageEvent)
    }

    fun deleteJob(job_id: String) = viewModelScope.launch {
        jobRepository.deleteJob(job_id = job_id)

        _uiState.update {
            it.copy(
                jobs = uiState.value.jobs.filter { job ->
                    job._id != job_id
                }
            )
        }
    }

    fun applyForJob(user: String, job_id: String) = viewModelScope.launch {
        jobRepository.applyForJob(
            user = user,
            job_id = job_id
        )

        _uiState.update { it ->
            it.copy(
                jobs = uiState.value.jobs.filter { job ->
                    job._id != job_id
                }
            )
        }
    }

    fun sendPageReview(user: String, user_image: String, text: String) = viewModelScope.launch {
        pageRepository.sendPageReview(
            user = user,
            page = uiState.value.page?.name ?: "",
            text = text,
            user_image = user_image
        )

        val page = uiState.value.page?.copy()

        if (page != null) {
            page.reviews = page.reviews.plus(PageReview(user = user, text = text, user_image = user_image))

            _uiState.update {it.copy(page = page)}
        }


    }
}