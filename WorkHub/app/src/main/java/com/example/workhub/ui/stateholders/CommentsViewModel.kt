package com.example.workhub.ui.stateholders

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workhub.data.repository.JobRepository
import com.example.workhub.data.repository.PageRepository
import com.example.workhub.data.repository.PostRepository
import com.example.workhub.data.repository.UserRepository
import com.example.workhub.data.retrofit.models.*
import com.example.workhub.data.retrofit.requests.AddCommentRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class CommentsUiState(
    val post: Post?,
    val user: User?,
    val page: Page?,
    val text: String
)

@HiltViewModel
class CommentsViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val postRepository: PostRepository,
    private val pageRepository: PageRepository
) : BaseViewModel<PageEvent>() {
    private val _uiState = MutableStateFlow(
        CommentsUiState(
            page = null,
            post = null,
            user = null,
            text = ""
        )
    )

    val uiState = _uiState.asStateFlow()

    fun setText(text: String) {
        _uiState.update { it.copy(text = text) }
    }

    fun getPost(post_id: String) = viewModelScope.launch {
        val post = postRepository.getPostById(post_id = post_id)
        _uiState.update { it.copy(post = post) }

        if (post.creator_type == 0) {
            val user: User = userRepository.getUserByEmail(post.creator)
            _uiState.update { it.copy(user = user) }
        } else {
            val page: Page = pageRepository.getPageByName(post.creator)
            _uiState.update { it.copy(page = page) }
        }
    }

    fun addComment(user: String, profile_image: String) = viewModelScope.launch {
        val addCommentRequest = AddCommentRequest(
            user = user,
            profile_image = profile_image,
            post_id = uiState.value.post?._id ?: "",
            text = uiState.value.text
        )

        postRepository.addComment(addCommentRequest)
        val post = uiState.value.post?.copy()
        if (post != null) {
            post.comments = post.comments.plus(Comment(user = user, profile_image = profile_image, text = uiState.value.text))
        }

        _uiState.update { it.copy(post = post) }
    }
}