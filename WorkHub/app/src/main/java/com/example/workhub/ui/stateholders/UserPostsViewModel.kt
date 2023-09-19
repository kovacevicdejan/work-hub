package com.example.workhub.ui.stateholders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workhub.data.repository.PostRepository
import com.example.workhub.data.repository.UserRepository
import com.example.workhub.data.retrofit.models.Post
import com.example.workhub.data.retrofit.models.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class UserPostsUiState(
    val posts: List<Post>,
    val user: User?
)

@HiltViewModel
class UserPostsViewModel @Inject constructor(
    private val postRepository: PostRepository,
    private val userRepository: UserRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(
        UserPostsUiState(
            posts = emptyList(),
            user = null
        )
    )

    val uiState = _uiState.asStateFlow()

    fun getPosts(email: String) = viewModelScope.launch {
        val user = userRepository.getUserByEmail(email = email)
        _uiState.update { it.copy(user = user) }

        val posts: List<Post> = postRepository.getUserPosts(uiState.value.user?.email ?: "")
        _uiState.update { it.copy(posts = posts) }
    }
}