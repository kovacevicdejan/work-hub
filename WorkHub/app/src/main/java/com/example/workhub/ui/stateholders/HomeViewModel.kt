package com.example.workhub.ui.stateholders

import androidx.lifecycle.viewModelScope
import com.example.workhub.data.repository.PageRepository
import com.example.workhub.data.repository.PostRepository
import com.example.workhub.data.repository.UserRepository
import com.example.workhub.data.retrofit.models.Page
import com.example.workhub.data.retrofit.models.Post
import com.example.workhub.data.retrofit.models.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.collections.HashMap

data class HomeUiState(
    val posts: List<Post>,
    val users: Map<String, User>,
    val pages: Map<String, Page>
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val postRepository: PostRepository,
    private val userRepository: UserRepository,
    private val pageRepository: PageRepository
) : BaseViewModel<ChatEvent>() {
    private val _uiState = MutableStateFlow(
        HomeUiState(
            posts = emptyList(),
            users = emptyMap(),
            pages = emptyMap()
        )
    )

    val uiState = _uiState.asStateFlow()

    fun getPosts(email: String) = viewModelScope.launch {
        val posts = postRepository.getPosts(email = email)
        _uiState.update { it.copy(posts = posts) }

        var users: Map<String, User> = emptyMap()
        var pages: Map<String, Page> = emptyMap()

        for(post in posts) {
            if(post.creator_type == 0) {
                if(!users.containsKey(post.creator)) {
                    val user = userRepository.getUserByEmail(post.creator)
                    users = users.plus(Pair(post.creator, user))
                }
            }
            else {
                if(!pages.containsKey(post.creator)) {
                    val page = pageRepository.getPageByName(post.creator)
                    pages = pages.plus(Pair(post.creator, page))
                }
            }
        }

        _uiState.update { it.copy(users = users) }
        _uiState.update { it.copy(pages = pages) }
    }
}